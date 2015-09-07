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
package com.android.tools.idea.gradle.dsl.parser;

import com.intellij.testFramework.CompositeException;
import com.intellij.testFramework.PlatformTestCase;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import static com.android.SdkConstants.FN_BUILD_GRADLE;
import static com.intellij.openapi.util.io.FileUtil.ensureCanCreateFile;
import static com.intellij.openapi.util.io.FileUtil.writeToFile;
import static org.fest.assertions.Assertions.assertThat;

public abstract class DslElementParserTestCase extends PlatformTestCase {
  protected File myBuildFile;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    File moduleFilePath = new File(myModule.getModuleFilePath());
    File moduleDirPath = moduleFilePath.getParentFile();
    assertThat(moduleDirPath).isDirectory();

    myBuildFile = new File(moduleDirPath, FN_BUILD_GRADLE);
    assertTrue(ensureCanCreateFile(myBuildFile));
  }

  @Override
  protected CompositeException checkForSettingsDamage() {
    return new CompositeException();
  }

  protected void writeToBuildFile(@NotNull String text) throws IOException {
    writeToFile(myBuildFile, text);
  }

  @NotNull
  protected GradleBuildModel getGradleBuildModel() {
    GradleBuildModel buildModel = GradleBuildModel.get(myModule);
    assertNotNull(buildModel);
    return buildModel;
  }
}
