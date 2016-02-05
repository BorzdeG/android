/*
 * Copyright (C) 2015 The Android Open Source Project
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
package com.android.tools.idea.ui.properties.adapters;

import com.android.tools.idea.ui.properties.ObservableProperty;
import org.jetbrains.annotations.NotNull;

/**
 * Adapter property that wraps a String type which represents a Integer value.
 *
 * If a string is passed in that isn't properly formatted, this adapter returns the last known
 * good value.
 */
public final class StringToIntAdapterProperty extends AdapterProperty<String, Integer> {

  private int lastGoodValue;

  public StringToIntAdapterProperty(@NotNull ObservableProperty<String> wrappedProperty) {
    super(wrappedProperty);
  }

  @NotNull
  @Override
  protected Integer convertFromSourceType(@NotNull String value) {
    try {
      lastGoodValue = Integer.parseInt(value);
      return lastGoodValue;
    }
    catch (NumberFormatException e) {
      return lastGoodValue;
    }
  }

  @NotNull
  @Override
  protected String convertFromDestType(@NotNull Integer value) {
    return Integer.toString(value);
  }
}
