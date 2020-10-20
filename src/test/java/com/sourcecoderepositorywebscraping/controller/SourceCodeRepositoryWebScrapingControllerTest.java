package com.sourcecoderepositorywebscraping.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sourcecoderepositorywebscraping.model.GroupDataByFileExtensionModel;

@TestInstance(Lifecycle.PER_CLASS)
public class SourceCodeRepositoryWebScrapingControllerTest extends AbstractTest {

   @Override
   @BeforeAll
   public void setUp() {
      super.setUp();
   }

   @Test
   public void whenUriCorretWithArgumentInvalid_thenAssertionSucceeds() throws Exception {
	   
	  String uri = "/fetchDataRepository?repositoryUrl=teste";
      
	  MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      String content = mvcResult.getResponse().getContentAsString();
      
      int status = mvcResult.getResponse().getStatus();
      
      GroupDataByFileExtensionModel result = super.mapFromJson(content, GroupDataByFileExtensionModel.class);
      
      assertThat(result.getHttpError() != null && status == 400);
   }

   @Test
   public void whenUriCorret_thenAssertionSucceeds() throws Exception {
	   
	  String uri = "/fetchDataRepository?repositoryUrl=https://github.com/fbentes/FrameworkWebDesk/blob/master/Estoque/DataObjectLayer.Estoque.SQLServer2005/DataObjectLayer.Estoque.SQLServer2005.csproj";
      
	  MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
      
      int status = mvcResult.getResponse().getStatus();
      
      assertThat(200 == status);  // OK
   }
}
