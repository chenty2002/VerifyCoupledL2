/** *************************************************************************************
  * Copyright (c) 2020-2021 Institute of Computing Technology, Chinese Academy of Sciences
  * Copyright (c) 2020-2021 Peng Cheng Laboratory
  *
  * XiangShan is licensed under Mulan PSL v2.
  * You can use this software according to the terms and conditions of the Mulan PSL v2.
  * You may obtain a copy of Mulan PSL v2 at:
  * http://license.coscl.org.cn/MulanPSL2
  *
  * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
  * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
  * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
  *
  * See the Mulan PSL v2 for more details.
  * *************************************************************************************
  */

package coupledL2AsL1.prefetch

import chisel3._
import chisel3.util._
import coupledL2.L2ToL1TlbIO
import org.chipsalliance.cde.config.Parameters
import coupledL2.prefetch._

case class CoupledL2AsL1PrefParam() extends PrefetchParameters {
  override val hasPrefetchBit: Boolean = true
  override val hasPrefetchSrc: Boolean = true
  override val inflightEntries: Int = 16
}

class PrefetchIO(implicit p: Parameters) extends PrefetchBundle {
  val train = Flipped(DecoupledIO(new PrefetchTrain))
  val tlb_req = new L2ToL1TlbIO(nRespDups= 1)
  val req = DecoupledIO(new PrefetchReq)
  val resp = Flipped(DecoupledIO(new PrefetchResp))
}

class Prefetcher(implicit p: Parameters) extends PrefetchModule {
  val io = IO(new PrefetchIO)
  io.tlb_req <> DontCare
  val hartId = IO(Input(UInt(hartIdLen.W)))
  val randomIO = IO(new Bundle() {
    val inputRandomAddr = Input(UInt(5.W))
    val inputNeedT = Input(new Bool)
  })

  prefetchers.head match {
    case _ =>
      println("--------------------------------")
      println("Prefetcher Modified for L2 as L1")
      println("--------------------------------")

      io.resp.ready := true.B
      io.train.ready := true.B

      io.req.valid := true.B
      val (tag, set, _) = parseAddress(randomIO.inputRandomAddr)
      io.req.bits.tag := tag
      io.req.bits.set := set
      io.req.bits.needT := randomIO.inputNeedT

      io.req.bits.pfSource := PfSource.NoWhere.id.U

      val reqSource = RegInit(hartId)
      when(io.req.valid && io.req.ready) {
        reqSource := reqSource + 2.U
      }
      io.req.bits.source := reqSource
  }
}
