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
package com.android.tools.idea.monitor.ui.network.view;

import com.android.tools.adtui.AccordionLayout;
import com.android.tools.adtui.Choreographer;
import com.android.tools.adtui.Range;
import com.android.tools.idea.monitor.ui.network.model.HttpDataPoller;
import com.android.tools.idea.monitor.ui.network.model.NetworkDataPoller;
import com.android.tools.idea.monitor.datastore.Poller;
import com.android.tools.idea.monitor.datastore.SeriesDataStore;
import com.android.tools.idea.monitor.ui.BaseProfilerUiManager;
import com.android.tools.idea.monitor.ui.BaseSegment;
import com.android.tools.idea.monitor.ui.ProfilerEventListener;
import com.google.common.collect.Sets;
import com.intellij.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import javax.swing.*;

public final class NetworkProfilerUiManager extends BaseProfilerUiManager {
  public static final int NETWORK_CONNECTIVITY_HEIGHT = 40;

  private NetworkRadioSegment myRadioSegment;

  public NetworkProfilerUiManager(@NotNull Range xRange, @NotNull Choreographer choreographer,
                                  @NotNull SeriesDataStore dataStore, @NotNull EventDispatcher<ProfilerEventListener> eventDispatcher) {
    super(xRange, choreographer, dataStore, eventDispatcher);
  }

  @NotNull
  @Override
  public Set<Poller> createPollers(int pid) {
    return Sets.newHashSet(new NetworkDataPoller(myDataStore, pid), new HttpDataPoller(myDataStore, pid));
  }

  @Override
  @NotNull
  protected BaseSegment createOverviewSegment(@NotNull Range xRange,
                                              @NotNull SeriesDataStore dataStore,
                                              @NotNull EventDispatcher<ProfilerEventListener> eventDispatcher) {
    return new NetworkSegment(xRange, dataStore, eventDispatcher);
  }

  @Override
  public void setupExtendedOverviewUi(@NotNull JPanel toolbar, @NotNull JPanel overviewPanel) {
    super.setupExtendedOverviewUi(toolbar, overviewPanel);
    myRadioSegment = new NetworkRadioSegment(myXRange, myDataStore, myEventDispatcher);
    setupAndRegisterSegment(myRadioSegment, NETWORK_CONNECTIVITY_HEIGHT, NETWORK_CONNECTIVITY_HEIGHT, NETWORK_CONNECTIVITY_HEIGHT);
    overviewPanel.add(myRadioSegment);
    setSegmentState(overviewPanel, myRadioSegment, AccordionLayout.AccordionState.MAXIMIZE);
  }

  @Override
  public void resetProfiler(@NotNull JPanel toolbar, @NotNull JPanel overviewPanel, @NotNull JPanel detailPanel) {
    super.resetProfiler(toolbar, overviewPanel, detailPanel);

    // TODO: un-register segments from choreographer
    overviewPanel.remove(myRadioSegment);
  }
}
