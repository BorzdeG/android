/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.tools.profilers.memory;

import com.android.tools.adtui.model.Range;
import com.android.tools.adtui.model.RangedContinuousSeries;
import com.android.tools.profiler.proto.MemoryProfiler;
import com.android.tools.profiler.proto.MemoryServiceGrpc;
import com.android.tools.profilers.ProfilerMonitor;
import com.android.tools.profilers.StudioProfilers;
import org.jetbrains.annotations.NotNull;

public class MemoryMonitor extends ProfilerMonitor {
  private final int myProcessId;

  @NotNull
  private final StudioProfilers myProfilers;

  public MemoryMonitor(@NotNull StudioProfilers profilers, int pid) {
    myProcessId = pid;
    myProfilers = profilers;
  }

  @NotNull
  public RangedContinuousSeries getTotalMemory() {
    MemoryServiceGrpc.MemoryServiceBlockingStub client = myProfilers.getClient().getMemoryClient();
    // TODO fix Range and expose it to Choreographer
    return new RangedContinuousSeries("Memory", myProfilers.getViewRange(), new Range(0, 1024 * 1024),
                                      new MemoryDataSeries(client, myProcessId) {
                                                                       @Override
                                                                       @NotNull
                                                                       public Long filterData(@NotNull MemoryProfiler.MemoryData.MemorySample sample) {
                                                                         return sample.getTotalMem();
                                                                       }
                                                                     });
  }

  public void expand() {
    myProfilers.setStage(new MemoryProfilerStage(myProfilers));
  }
}