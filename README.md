# VerifyCoupledL2

Verification codes for OpenXiangShan CoupledL2/HuanCun system

## Chisel Codes

See directory `CoupledL2-Chisel`.

The verification codes are mainly based on assertions:

| Position                                         | Description                                                  | Expected Result |
| ------------------------------------------------ | ------------------------------------------------------------ | --------------- |
| src\main\scala\coupledL2\tl2tl\MSHRCtl.scala 213 | Deadlock Location (cex meaning a deadlock found)             | Unknown         |
| src\main\scala\coupledL2\Directory.scala 378-285 | System Functionality Indicator (cex meaning the system works fine) | All Cex         |
| src\test\scala\coupledL2\VerifyTop.scala 216-221 | Mutual Property Verification (still developing)              | All Cex         |

For Chisel to Verilog compilation, at directory `CoupledL2-Chisel`, run

```shell
mill -i CoupledL2.test.runMain coupledL2.VerifyTop_L2L3L2
```

or

```shell
make verify
```

This will generate the SystemVerilog codes of CoupledL2 including verification assertions in directory `CoupledL2-Chisel/Verilog/L2L3L2/`.

## Verilog Codes

See directory `CoupledL2-Verilog`

The Verilog codes are (in directory `CoupledL2-Verilog/code`): `VerifyTop.sv`, `LogPerfHelper.v`, `ResetCounter.sv`, `STD_CLKGT_func.v`, `ClockGate.v`, `TLLogWriter.v`, and the top module is `VerifyTop` in `VerifyTop.sv`. An example script for JasperGold is provided as `jg.tcl`.

The verification codes are mainly based on assertions:

| Position                                             | Description                                                  | Expected Result |
| ---------------------------------------------------- | ------------------------------------------------------------ | --------------- |
| code/VerifyTop.sv 67516-67530 (module `MSHRCtl_2`)   | Deadlock Location (cex meaning a deadlock found)             | Unknown         |
| code/VerifyTop.sv 69120-69142 (module `Directory_2`) | System Functionality Indicator (cex meaning the system works fine) | All Cex         |
| code/VerifyTop.sv 87996-88018 (module `Directory_3`) | System Functionality Indicator (cex meaning the system works fine) | All Cex         |
| code/VerifyTop.sv 149405-149427 (module `VerifyTop`) | Mutual Property Verification (still developing)              | All Cex         |