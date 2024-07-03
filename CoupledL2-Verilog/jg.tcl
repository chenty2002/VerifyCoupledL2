analyze -sv code/VerifyTop.sv code/LogPerfHelper.v code/ResetCounter.sv code/STD_CLKGT_func.v code/ClockGate.v code/TLLogWriter.v
elaborate
reset reset
clock clock
set_prove_time_limit 0s
set_engine_threads 16
set_proofgrid_per_engine_max_jobs 32
