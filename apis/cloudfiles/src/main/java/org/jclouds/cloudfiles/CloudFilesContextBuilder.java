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
package org.jclouds.cloudfiles;

import java.util.List;

import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.BlobStoreContextBuilder;
import org.jclouds.cloudfiles.blobstore.config.CloudFilesBlobStoreContextModule;
import org.jclouds.cloudfiles.config.CloudFilesRestClientModule;
import org.jclouds.http.config.JavaUrlHttpCommandExecutorServiceModule;
import org.jclouds.logging.jdk.config.JDKLoggingModule;
import org.jclouds.providers.ProviderMetadata;

import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Creates {@link CloudFilesStoreContext} or {@link Injector} instances based on
 * the most commonly requested arguments.
 * <p/>
 * Note that Threadsafe objects will be bound as singletons to the Injector or
 * Context provided.
 * <p/>
 * <p/>
 * If no <code>Module</code>s are specified, the default
 * {@link JDKLoggingModule logging} and
 * {@link JavaUrlHttpCommandExecutorServiceModule http transports} will be
 * installed.
 * 
 * @author Adrian Cole, Andrew Newdigate
 * @see CloudFilesStoreContext
 */
public class CloudFilesContextBuilder
      extends
      BlobStoreContextBuilder<CloudFilesClient, CloudFilesAsyncClient, BlobStoreContext<CloudFilesClient, CloudFilesAsyncClient>, CloudFilesApiMetadata> {

   public CloudFilesContextBuilder(
         ProviderMetadata<CloudFilesClient, CloudFilesAsyncClient, BlobStoreContext<CloudFilesClient, CloudFilesAsyncClient>, CloudFilesApiMetadata> providerMetadata) {
      super(providerMetadata);
   }

   public CloudFilesContextBuilder(CloudFilesApiMetadata apiMetadata) {
      super(apiMetadata);
   }

   @Override
   protected void addContextModule(List<Module> modules) {
      modules.add(new CloudFilesBlobStoreContextModule());
   }

   @Override
   protected void addClientModule(List<Module> modules) {
      modules.add(new CloudFilesRestClientModule());
   }
}
