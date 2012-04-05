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
package org.jclouds.carrenza.vcloud.director;

import java.util.List;

import org.jclouds.carrenza.vcloud.director.config.CarrenzaVCloudDirectorRestClientModule;
import org.jclouds.providers.ProviderMetadata;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorApiMetadata;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorContext;
import org.jclouds.vcloud.director.v1_5.VCloudDirectorContextBuilder;
import org.jclouds.vcloud.director.v1_5.user.VCloudDirectorAsyncClient;
import org.jclouds.vcloud.director.v1_5.user.VCloudDirectorClient;

import com.google.inject.Module;

/**
 * {@inheritDoc} 
 *
 * @author danikov
 */
public class CarrenzaVCloudDirectorContextBuilder extends VCloudDirectorContextBuilder {

   public CarrenzaVCloudDirectorContextBuilder(
         ProviderMetadata<VCloudDirectorClient, VCloudDirectorAsyncClient, VCloudDirectorContext, VCloudDirectorApiMetadata> providerMetadata) {
      super(providerMetadata);
   }

   public CarrenzaVCloudDirectorContextBuilder(VCloudDirectorApiMetadata apiMetadata) {
      super(apiMetadata);
   }

   @Override
   protected void addContextModule(List<Module> modules) {
//      modules.add(new CarrenzaVCloudDirectorComputeServiceContextModule()); FIXME: enable when compute service done
   }

   @Override
   protected void addClientModule(List<Module> modules) {
      modules.add(new CarrenzaVCloudDirectorRestClientModule());
   }

}
