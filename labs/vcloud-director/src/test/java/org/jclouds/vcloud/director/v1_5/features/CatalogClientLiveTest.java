/*
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
package org.jclouds.vcloud.director.v1_5.features;

import static com.google.common.base.Predicates.and;
import static com.google.common.collect.Iterables.find;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorLiveTestConstants.CORRECT_VALUE_OBJECT_FMT;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorLiveTestConstants.OBJ_REQ_LIVE;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorLiveTestConstants.REF_REQ_LIVE;
import static org.jclouds.vcloud.director.v1_5.VCloudDirectorLiveTestConstants.TASK_COMPLETE_TIMELY;
import static org.jclouds.vcloud.director.v1_5.domain.Checks.checkCatalogItem;
import static org.jclouds.vcloud.director.v1_5.domain.Checks.checkMetadata;
import static org.jclouds.vcloud.director.v1_5.domain.Checks.checkMetadataValue;
import static org.jclouds.vcloud.director.v1_5.domain.Checks.checkTask;
import static org.jclouds.vcloud.director.v1_5.predicates.LinkPredicates.relEquals;
import static org.jclouds.vcloud.director.v1_5.predicates.LinkPredicates.typeEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.jclouds.vcloud.director.v1_5.VCloudDirectorMediaType;
import org.jclouds.vcloud.director.v1_5.domain.AdminCatalog;
import org.jclouds.vcloud.director.v1_5.domain.CatalogItem;
import org.jclouds.vcloud.director.v1_5.domain.CatalogType;
import org.jclouds.vcloud.director.v1_5.domain.Checks;
import org.jclouds.vcloud.director.v1_5.domain.Link;
import org.jclouds.vcloud.director.v1_5.domain.Media;
import org.jclouds.vcloud.director.v1_5.domain.Metadata;
import org.jclouds.vcloud.director.v1_5.domain.MetadataEntry;
import org.jclouds.vcloud.director.v1_5.domain.MetadataValue;
import org.jclouds.vcloud.director.v1_5.domain.Reference;
import org.jclouds.vcloud.director.v1_5.domain.Task;
import org.jclouds.vcloud.director.v1_5.domain.Vdc;
import org.jclouds.vcloud.director.v1_5.features.admin.AdminCatalogClient;
import org.jclouds.vcloud.director.v1_5.internal.BaseVCloudDirectorClientLiveTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Tests live behavior of {@link CatalogClient}.
 * 
 * @author grkvlt@apache.org
 */
@Test(groups = { "live", "user" }, singleThreaded = true, testName = "CatalogClientLiveTest")
public class CatalogClientLiveTest extends BaseVCloudDirectorClientLiveTest {

   /*
    * Convenience references to API clients.
    */
   private CatalogClient catalogClient;

   /*
    * Shared state between dependant tests.
    */
   private AdminCatalog adminCatalog;
   private Media media;
   private CatalogItem catalogItem;

   private Reference catalogRef;

   @Override
   protected void setupRequiredClients() {
      // TODO why do I need a guard clause here?
      if (adminCatalog != null) return;
      catalogClient = context.getApi().getCatalogClient();
      Reference orgRef = Iterables.getFirst(context.getApi().getOrgClient().getOrgList().getOrgs(), null).toAdminReference(endpoint);
      
      if (adminContext != null) {
         AdminCatalog newCatalog = AdminCatalog.builder()
               .name(name("Test Catalog "))
               .description("created by CatalogClientLiveTest")
               .build();
         
         AdminCatalogClient adminCatalogClient = adminContext.getApi().getCatalogClient();
         adminCatalog = adminCatalogClient.createCatalog(orgRef.getHref(), newCatalog);
         catalogRef = find(adminCatalog.getLinks(), and(relEquals("alternate"), typeEquals(VCloudDirectorMediaType.CATALOG)));
   
         Metadata newMetadata = Metadata.builder()
               .entry(MetadataEntry.builder().entry("KEY", "MARMALADE").build())
               .build();
   
         Task mergeCatalogMetadata = adminCatalogClient.getMetadataClient().mergeMetadata(adminCatalog.getHref(), newMetadata);
         checkTask(mergeCatalogMetadata);
         assertTrue(retryTaskSuccess.apply(mergeCatalogMetadata), String.format(TASK_COMPLETE_TIMELY, "setupRequiredClients"));
      } else {
         catalogRef = Reference.builder().href(catalogURI).build();
      }
   }
   
   @AfterClass(alwaysRun = true)
   public void tearDown() {
      if (catalogItem != null) {
         try {
	         catalogClient.deleteCatalogItem(catalogItem.getHref());
         } catch (Exception e) {
            logger.warn(e, "Error when deleting catalog item '%s'", catalogItem.getName());
         }
      }
      if (media != null) {
         try {
	         Task delete = context.getApi().getMediaClient().deleteMedia(media.getHref());
	         taskDoneEventually(delete);
         } catch (Exception e) {
            logger.warn(e, "Error when deleting media '%s'", media.getName());
         }
      }
      if (adminContext != null && adminCatalog != null) {
         try {
	         adminContext.getApi().getCatalogClient().deleteCatalog(adminCatalog.getHref());
         } catch (Exception e) {
            logger.warn(e, "Error when deleting catalog '%s'", adminCatalog.getName());
         }
      }
      // TODO wait for tasks
   }

   @Test(description = "GET /catalog/{id}")
   public void testGetCatalog() {
      CatalogType catalog = catalogClient.getCatalog(catalogRef.getHref());
      assertNotNull(catalog);
      // Double check it's pointing at the correct catalog
      assertEquals(catalog.getHref(), catalogRef.getHref());
   }

   @Test(description = "GET /catalogItem/{id}", dependsOnMethods = "testAddCatalogItem")
   public void testGetCatalogItem() {
      CatalogItem catalogItem = catalogClient.getCatalogItem(this.catalogItem.getHref());
      checkCatalogItem(catalogItem);
      assertEquals(catalogItem.getEntity().getHref(), this.catalogItem.getEntity().getHref());
   }

   @Test(description = "POST /catalog/{id}/catalogItems")
   public void testAddCatalogItem() {
      assertNotNull(vdcURI, String.format(REF_REQ_LIVE, VDC));
      
      byte[] iso = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
      Vdc vdc = context.getApi().getVdcClient().getVdc(vdcURI);
      assertNotNull(vdc, String.format(OBJ_REQ_LIVE, VDC));
      Link addMedia = find(vdc.getLinks(), and(relEquals("add"), typeEquals(VCloudDirectorMediaType.MEDIA)));
     
      Media sourceMedia = Media.builder()
            .type(VCloudDirectorMediaType.MEDIA)
            .name("Test media 1")
            .size(iso.length)
            .imageType(Media.ImageType.ISO)
            .description("Test media generated by testCreateMedia()")
            .build();
      media = context.getApi().getMediaClient().createMedia(addMedia.getHref(), sourceMedia);

      Checks.checkMediaFor(VCloudDirectorMediaType.MEDIA, media);

      CatalogItem editedCatalogItem = CatalogItem.builder()
            .name("newitem")
            .description("New Item")
            .type(VCloudDirectorMediaType.CATALOG_ITEM)
            .entity(Reference.builder().href(media.getHref()).build())
            .build();
      catalogItem = catalogClient.addCatalogItem(catalogRef.getHref(), editedCatalogItem);
      checkCatalogItem(catalogItem);
      assertEquals(catalogItem.getName(), "newitem");
      assertEquals(catalogItem.getDescription(), "New Item");
   }

   @Test(description = "PUT /catalogItem/{id}", dependsOnMethods = "testAddCatalogItem")
   public void testUpdateCatalogItem() {     
      CatalogItem updatedCatalogItem = CatalogItem.builder().fromCatalogItem(catalogItem).name("UPDATEDNAME").build();
      catalogItem = catalogClient.updateCatalogItem(catalogItem.getHref(), updatedCatalogItem);
      checkCatalogItem(catalogItem);
      assertEquals(catalogItem.getName(), "UPDATEDNAME");
   }

   // Note this runs after all the metadata tests
   @Test(description = "DELETE /catalogItem/{id}", dependsOnMethods = "testDeleteCatalogItemMetadataValue")
   public void testDeleteCatalogItem() {
      catalogClient.deleteCatalogItem(catalogItem.getHref());
      catalogItem = catalogClient.getCatalogItem(catalogItem.getHref());
      assertNull(catalogItem);
   }

   @Test(description = "GET /catalog/{id}/metadata")
   public void testGetCatalogMetadata() {
      Metadata catalogMetadata = catalogClient.getMetadataClient().getMetadata(catalogRef.getHref());
      checkMetadata(catalogMetadata);
   }

   @Test(description = "GET /catalog/{id}/metadata/{key}")
   public void testGetCatalogMetadataValue() {
      Metadata catalogMetadata = catalogClient.getMetadataClient().getMetadata(catalogRef.getHref());
      MetadataEntry existingMetadataEntry = Iterables.find(catalogMetadata.getMetadataEntries(), new Predicate<MetadataEntry>() {
         @Override
         public boolean apply(MetadataEntry input) {
            return input.getKey().equals("KEY");
         }
      });
      MetadataValue metadataValue = catalogClient.getMetadataClient().getMetadataValue(catalogRef.getHref(), "KEY");
      assertEquals(metadataValue.getValue(), existingMetadataEntry.getValue(),
            String.format(CORRECT_VALUE_OBJECT_FMT, "Value", "MetadataValue", existingMetadataEntry.getValue(), metadataValue.getValue()));
      checkMetadataValue(metadataValue);
   }

   @Test(description = "GET /catalogItem/{id}/metadata", dependsOnMethods = "testAddCatalogItem")
   public void testGetCatalogItemMetadata() {
      Metadata catalogItemMetadata = catalogClient.getCatalogItemMetadataClient().getMetadata(catalogItem.getHref());
      checkMetadata(catalogItemMetadata);
   }

   @Test(description = "POST /catalogItem/{id}/metadata", dependsOnMethods = "testAddCatalogItem")
   public void testMergeCatalogItemMetadata() {
      Metadata newMetadata = Metadata.builder()
            .entry(MetadataEntry.builder().entry("KEY", "MARMALADE").build())
            .entry(MetadataEntry.builder().entry("VEGIMITE", "VALUE").build())
            .build();

      Metadata before = catalogClient.getCatalogItemMetadataClient().getMetadata(catalogItem.getHref());
   
      Task mergeCatalogItemMetadata = catalogClient.getCatalogItemMetadataClient().mergeMetadata(catalogItem.getHref(), newMetadata);
      checkTask(mergeCatalogItemMetadata);
      assertTrue(retryTaskSuccess.apply(mergeCatalogItemMetadata),
            String.format(TASK_COMPLETE_TIMELY, "mergeCatalogItemMetadata"));
      Metadata mergedCatalogItemMetadata = catalogClient.getCatalogItemMetadataClient().getMetadata(catalogItem.getHref());

      assertTrue(mergedCatalogItemMetadata.getMetadataEntries().size() > before.getMetadataEntries().size(),
            "Should have added at least one other MetadataEntry to the CatalogItem");
      
      MetadataValue keyMetadataValue = catalogClient.getCatalogItemMetadataClient().getMetadataValue(catalogItem.getHref(), "KEY");
      assertEquals(keyMetadataValue.getValue(), "MARMALADE", "The Value of the MetadataValue for KEY should have changed");
      checkMetadataValue(keyMetadataValue);
      
      MetadataValue newKeyMetadataValue = catalogClient.getCatalogItemMetadataClient().getMetadataValue(catalogItem.getHref(), "VEGIMITE");

      assertEquals(newKeyMetadataValue.getValue(), "VALUE", "The Value of the MetadataValue for NEW_KEY should have been set");
      checkMetadataValue(newKeyMetadataValue);
   }

   @Test(description = "GET /catalogItem/{id}/metadata/{key}", dependsOnMethods = "testSetCatalogItemMetadataValue")
   public void testGetCatalogItemMetadataValue() {      
      MetadataValue metadataValue = catalogClient.getCatalogItemMetadataClient().getMetadataValue(catalogItem.getHref(), "KEY");
      checkMetadataValue(metadataValue);
   }

   @Test(description = "PUT /catalogItem/{id}/metadata/{key}", dependsOnMethods = "testMergeCatalogItemMetadata")
   public void testSetCatalogItemMetadataValue() {
      MetadataValue newMetadataValue = MetadataValue.builder().value("NEW").build();

      Task setCatalogItemMetadataValue = catalogClient.getCatalogItemMetadataClient().setMetadata(catalogItem.getHref(), "KEY", newMetadataValue);
      checkTask(setCatalogItemMetadataValue);
      assertTrue(retryTaskSuccess.apply(setCatalogItemMetadataValue), 
            String.format(TASK_COMPLETE_TIMELY, "setCatalogItemMetadataValue"));
      
      MetadataValue updatedMetadataValue = catalogClient.getCatalogItemMetadataClient().getMetadataValue(catalogItem.getHref(), "KEY");
      assertEquals(updatedMetadataValue.getValue(), newMetadataValue.getValue(),
               String.format(CORRECT_VALUE_OBJECT_FMT, "Value", "MetadataValue", newMetadataValue.getValue(), updatedMetadataValue.getValue()));
      checkMetadataValue(updatedMetadataValue);
   }

   @Test(description = "DELETE /catalogItem/{id}/metadata/{key}", dependsOnMethods = "testGetCatalogItemMetadataValue")
   public void testDeleteCatalogItemMetadataValue() {
      Task deleteCatalogItemMetadataValue = catalogClient.getCatalogItemMetadataClient().deleteMetadataEntry(catalogItem.getHref(), "KEY");
      checkTask(deleteCatalogItemMetadataValue);
      assertTrue(retryTaskSuccess.apply(deleteCatalogItemMetadataValue), 
            String.format(TASK_COMPLETE_TIMELY, "deleteCatalogItemMetadataValue"));
      MetadataValue deleted = catalogClient.getMetadataClient().getMetadataValue(catalogItem.getHref(), "KEY");
      assertNull(deleted);
   }
}
