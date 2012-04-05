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
package org.jclouds.eucalyptus;

import static org.jclouds.compute.config.ComputeServiceProperties.TIMEOUT_PORT_OPEN;
import static org.jclouds.ec2.reference.EC2Constants.PROPERTY_EC2_AMI_OWNERS;
import static org.jclouds.location.reference.LocationConstants.PROPERTY_REGIONS;

import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.ec2.EC2ApiMetadata;
import org.jclouds.ec2.EC2AsyncClient;
import org.jclouds.ec2.EC2Client;
import org.jclouds.ec2.compute.EC2ComputeServiceContext;

/**
 * Implementation of {@link ApiMetadata} for the Eucalyptus (EC2 clone) api.
 * 
 * @author Adrian Cole
 */
public class EucalyptusApiMetadata extends EC2ApiMetadata<EC2Client, EC2AsyncClient, EC2ComputeServiceContext<EC2Client, EC2AsyncClient>, EucalyptusApiMetadata> {
   private static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return builder().fromApiMetadata(this);
   }

   public EucalyptusApiMetadata() {
      this(builder());
   }

   protected EucalyptusApiMetadata(Builder builder) {
      super(builder);
   }
   
   protected static Properties defaultProperties() {
      Properties properties = EC2ApiMetadata.defaultProperties();
      properties.setProperty(PROPERTY_REGIONS, "Eucalyptus");
      properties.setProperty(PROPERTY_EC2_AMI_OWNERS, "admin");
      properties.setProperty(TIMEOUT_PORT_OPEN, 5 * 60 * 1000 + "");
      return properties;
   }

   public static class Builder extends EC2ApiMetadata.Builder<EC2Client, EC2AsyncClient, EC2ComputeServiceContext<EC2Client, EC2AsyncClient>, EucalyptusApiMetadata> {
      protected Builder(){
         super(EC2Client.class, EC2AsyncClient.class);
         id("eucalyptus")
         .defaultEndpoint("http://173.205.188.130:8773/services/Eucalyptus")
         .name("Eucalyptus (EC2 clone) API")
         .defaultProperties(EucalyptusApiMetadata.defaultProperties());
      }
      
      @Override
      public EucalyptusApiMetadata build() {
         return new EucalyptusApiMetadata(this);
      }

      @Override
      public Builder fromApiMetadata(EucalyptusApiMetadata in) {
         super.fromApiMetadata(in);
         return this;
      }
   }

}