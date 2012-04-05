/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.rimuhosting.miro.compute;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.internal.BaseComputeServiceLiveTest;
import org.jclouds.rimuhosting.miro.RimuHostingAsyncClient;
import org.jclouds.rimuhosting.miro.RimuHostingClient;
import org.jclouds.ssh.jsch.config.JschSshClientModule;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

/**
 * @author Ivan Meredith
 */
@Test(groups = "live", sequential = true)
public class RimuHostingComputeServiceLiveTest
      extends
      BaseComputeServiceLiveTest<RimuHostingClient, RimuHostingAsyncClient, ComputeServiceContext<RimuHostingClient, RimuHostingAsyncClient>> {
  
   public RimuHostingComputeServiceLiveTest() {
      provider = "rimuhosting";
   }

   @BeforeClass
   @Override
   public void setServiceDefaults() {
      group = "rimuhosting.jclouds";
   }

   // rimuhosting does not support metadata
   @Override
   protected void checkUserMetadataInNodeEquals(NodeMetadata node, ImmutableMap<String, String> userMetadata) {
      assert node.getUserMetadata().equals(ImmutableMap.<String, String> of()) : String.format(
            "node userMetadata did not match %s %s", userMetadata, node);
   }
   
   @Override
   protected JschSshClientModule getSshModule() {
      return new JschSshClientModule();
   }

   @Override
   public void testOptionToNotBlock() {
      // start call is blocking anyway.
   }
}
