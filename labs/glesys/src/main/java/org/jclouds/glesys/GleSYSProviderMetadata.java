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
package org.jclouds.glesys;

import static org.jclouds.glesys.reference.GleSYSConstants.PROPERTY_GLESYS_DEFAULT_DC;
import static org.jclouds.location.reference.LocationConstants.ISO3166_CODES;
import static org.jclouds.location.reference.LocationConstants.PROPERTY_ZONE;
import static org.jclouds.location.reference.LocationConstants.PROPERTY_ZONES;

import java.net.URI;
import java.util.Properties;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.internal.BaseProviderMetadata;

/**
 * Implementation of {@link org.jclouds.types.ProviderMetadata} for GleSYS.
 * @author Adrian Cole
 */
public class GleSYSProviderMetadata
      extends
      BaseProviderMetadata<GleSYSClient, GleSYSAsyncClient, ComputeServiceContext<GleSYSClient, GleSYSAsyncClient>, GleSYSApiMetadata> {

   public static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return builder().fromProviderMetadata(this);
   }

   public GleSYSProviderMetadata() {
      super(builder());
   }

   public GleSYSProviderMetadata(Builder builder) {
      super(builder);
   }

   protected static Properties defaultProperties() {
      Properties properties = new Properties();
      properties.setProperty(PROPERTY_ZONES, "Amsterdam,Falkenberg,New York City,Stockholm");
      properties.setProperty(PROPERTY_ZONE + ".Amsterdam." + ISO3166_CODES, "NL-NH");
      properties.setProperty(PROPERTY_ZONE + ".Falkenberg." + ISO3166_CODES, "SE-N");
      properties.setProperty(PROPERTY_ZONE + ".New York City." + ISO3166_CODES, "US-NY");
      properties.setProperty(PROPERTY_ZONE + ".Stockholm." + ISO3166_CODES, "SE-AB");
      properties.setProperty(PROPERTY_GLESYS_DEFAULT_DC, "Falkenberg");
      return properties;
   }

   public static class Builder
         extends
         BaseProviderMetadata.Builder<GleSYSClient, GleSYSAsyncClient, ComputeServiceContext<GleSYSClient, GleSYSAsyncClient>, GleSYSApiMetadata> {

      protected Builder() {
         id("glesys")
         .name("GleSYS")
         .apiMetadata(new GleSYSApiMetadata())
         .homepage(URI.create("http://www.glesys.com"))
         .console(URI.create("https://customer.glesys.com/cloud.php"))
         .iso3166Codes("NL-NH","SE-N","US-NY","SE-AB")
         .endpoint("https://api.glesys.com")
         .defaultProperties(GleSYSProviderMetadata.defaultProperties());
      }

      @Override
      public GleSYSProviderMetadata build() {
         return new GleSYSProviderMetadata(this);
      }

      @Override
      public Builder fromProviderMetadata(
            ProviderMetadata<GleSYSClient, GleSYSAsyncClient, ComputeServiceContext<GleSYSClient, GleSYSAsyncClient>, GleSYSApiMetadata> in) {
         super.fromProviderMetadata(in);
         return this;
      }

   }
}