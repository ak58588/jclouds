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
package org.jclouds.ec2.compute.internal;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.compute.Utils;
import org.jclouds.compute.internal.ComputeServiceContextImpl;
import org.jclouds.domain.Credentials;
import org.jclouds.ec2.EC2AsyncClient;
import org.jclouds.ec2.EC2Client;
import org.jclouds.ec2.compute.EC2ComputeService;
import org.jclouds.ec2.compute.EC2ComputeServiceContext;
import org.jclouds.rest.RestContext;

/**
 * @author Adrian Cole
 */
@Singleton
public class EC2ComputeServiceContextImpl<S extends EC2Client, A extends EC2AsyncClient> extends
      ComputeServiceContextImpl<S, A> implements EC2ComputeServiceContext<S, A> {
   @Inject
   public EC2ComputeServiceContextImpl(EC2ComputeService computeService, Map<String, Credentials> credentialStore,
         Utils utils, @SuppressWarnings("rawtypes") RestContext providerSpecificContext) {
      super(computeService, credentialStore, utils, providerSpecificContext);
   }

   @Override
   public EC2ComputeService getComputeService() {
      return EC2ComputeService.class.cast(super.getComputeService());
   }

}
