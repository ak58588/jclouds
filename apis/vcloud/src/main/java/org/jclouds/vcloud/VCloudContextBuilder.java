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
package org.jclouds.vcloud;

import java.util.List;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextBuilder;
import org.jclouds.providers.ProviderMetadata;
import org.jclouds.vcloud.compute.config.VCloudComputeServiceContextModule;
import org.jclouds.vcloud.config.VCloudRestClientModule;

import com.google.inject.Module;

/**
 * 
 * @author Adrian Cole
 */
public class VCloudContextBuilder
      extends
      ComputeServiceContextBuilder<VCloudClient, VCloudAsyncClient, ComputeServiceContext<VCloudClient, VCloudAsyncClient>, VCloudApiMetadata> {

   public VCloudContextBuilder(
         ProviderMetadata<VCloudClient, VCloudAsyncClient, ComputeServiceContext<VCloudClient, VCloudAsyncClient>, VCloudApiMetadata> providerMetadata) {
      super(providerMetadata);
   }

   public VCloudContextBuilder(VCloudApiMetadata apiMetadata) {
      super(apiMetadata);
   }

   @Override
   protected void addContextModule(List<Module> modules) {
      modules.add(new VCloudComputeServiceContextModule());
   }

   protected void addClientModule(List<Module> modules) {
      modules.add(new VCloudRestClientModule());
   }

}
