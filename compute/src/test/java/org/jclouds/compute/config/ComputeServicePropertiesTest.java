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
package org.jclouds.compute.config;

import static org.testng.Assert.assertEquals;

import java.util.Properties;

import org.jclouds.compute.ComputeServiceContextBuilder;
import org.jclouds.compute.reference.ComputeServiceConstants.InitStatusProperties;
import org.testng.annotations.Test;

/**
 * 
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "ComputeServicePropertiesTest")
public class ComputeServicePropertiesTest {
   public void testDefaultInitStatusProperties() {
      InitStatusProperties props = ComputeServiceContextBuilder.forTests().buildInjector()
            .getInstance(InitStatusProperties.class);
      assertEquals(props.initStatusInitialPeriod, 500);
      assertEquals(props.initStatusMaxPeriod, 5000);
   }

   public void testOverrideInitStatusProperties() {
      Properties overrides = new Properties();
      overrides.setProperty(ComputeServiceProperties.INIT_STATUS_INITIAL_PERIOD, "501");
      overrides.setProperty(ComputeServiceProperties.INIT_STATUS_MAX_PERIOD, "5001");
      
      InitStatusProperties props = ComputeServiceContextBuilder.forTests().overrides(overrides).buildInjector()
            .getInstance(InitStatusProperties.class);
      
      assertEquals(props.initStatusInitialPeriod, 501);
      assertEquals(props.initStatusMaxPeriod, 5001);
   }

}
