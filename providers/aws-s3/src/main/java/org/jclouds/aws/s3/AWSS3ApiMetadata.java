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
package org.jclouds.aws.s3;

import java.util.Properties;

import org.jclouds.apis.ApiMetadata;
import org.jclouds.aws.s3.blobstore.AWSS3BlobStoreContext;
import org.jclouds.s3.S3ApiMetadata;

import com.google.common.reflect.TypeToken;

/**
 * Implementation of {@link ApiMetadata} for the Amazon-specific S3 API
 * 
 * @author Adrian Cole
 */
public class AWSS3ApiMetadata extends S3ApiMetadata<AWSS3Client, AWSS3AsyncClient, AWSS3BlobStoreContext, AWSS3ApiMetadata> {
   private static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return builder().fromApiMetadata(this);
   }

   public AWSS3ApiMetadata() {
      this(builder());
   }

   protected AWSS3ApiMetadata(Builder builder) {
      super(builder);
   }
   
   protected static Properties defaultProperties() {
      Properties properties = S3ApiMetadata.defaultProperties();
      return properties;
   }

   public static class Builder extends S3ApiMetadata.Builder<AWSS3Client, AWSS3AsyncClient, AWSS3BlobStoreContext, AWSS3ApiMetadata> {
      protected Builder(){
         super(AWSS3Client.class, AWSS3AsyncClient.class);
         id("aws-s3")
         .name("Amazon-specific S3 API")
         .defaultProperties(AWSS3ApiMetadata.defaultProperties())
         .context(TypeToken.of(AWSS3BlobStoreContext.class))
         .contextBuilder(TypeToken.of(AWSS3ContextBuilder.class));
      }
      
      @Override
      public AWSS3ApiMetadata build() {
         return new AWSS3ApiMetadata(this);
      }

      @Override
      public Builder fromApiMetadata(AWSS3ApiMetadata in) {
         super.fromApiMetadata(in);
         return this;
      }
   }

}