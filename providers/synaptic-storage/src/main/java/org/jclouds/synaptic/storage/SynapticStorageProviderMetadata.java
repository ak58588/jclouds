package org.jclouds.synaptic.storage;

import java.net.URI;
import java.util.Properties;

import org.jclouds.atmos.AtmosApiMetadata;
import org.jclouds.atmos.AtmosAsyncClient;
import org.jclouds.atmos.AtmosClient;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.providers.ProviderMetadata;
import org.jclouds.providers.internal.BaseProviderMetadata;

/**
 * Implementation of {@link org.jclouds.types.ProviderMetadata} for AT&T's
 * Synaptic Storage provider.
 * 
 * @author Jeremy Whitlock <jwhitlock@apache.org>
 */
public class SynapticStorageProviderMetadata extends BaseProviderMetadata<AtmosClient, AtmosAsyncClient, BlobStoreContext<AtmosClient, AtmosAsyncClient>, AtmosApiMetadata> {
   
   public static Builder builder() {
      return new Builder();
   }

   @Override
   public Builder toBuilder() {
      return builder().fromProviderMetadata(this);
   }
   
   public SynapticStorageProviderMetadata() {
      super(builder());
   }

   public SynapticStorageProviderMetadata(Builder builder) {
      super(builder);
   }

   protected static Properties defaultProperties() {
      Properties properties = new Properties();
      return properties;
   }
   
   public static class Builder extends BaseProviderMetadata.Builder<AtmosClient, AtmosAsyncClient, BlobStoreContext<AtmosClient, AtmosAsyncClient>, AtmosApiMetadata> {

      protected Builder(){
         id("synaptic-storage")
         .name("AT&T Synaptic Storage")
         .apiMetadata(new AtmosApiMetadata())
         .homepage(URI.create("https://www.synaptic.att.com/"))
         .console(URI.create("https://www.synaptic.att.com/clouduser/login.htm"))
         .iso3166Codes("US-VA", "US-TX")
         .endpoint("https://storage.synaptic.att.com")
         .defaultProperties(SynapticStorageProviderMetadata.defaultProperties());
      }

      @Override
      public SynapticStorageProviderMetadata build() {
         return new SynapticStorageProviderMetadata(this);
      }
      
      @Override
      public Builder fromProviderMetadata(
            ProviderMetadata<AtmosClient, AtmosAsyncClient, BlobStoreContext<AtmosClient, AtmosAsyncClient>, AtmosApiMetadata> in) {
         super.fromProviderMetadata(in);
         return this;
      }
   }
}