package com.epam.loader.common.service.search;

import com.epam.loader.config.condition.DownloadableFile;
import com.epam.loader.common.util.CommonUtilException;
import com.epam.loader.common.service.ServiceException;
import com.epam.loader.plan.strategy.SearchStrategy;
import com.epam.loader.plan.strategy.impl.HdpSearchStrategy;
import com.epam.loader.common.util.CommonUtilHolder;
import com.epam.loader.common.util.HttpCommonUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith( PowerMockRunner.class )
@PrepareForTest( CommonUtilHolder.class )
public class HttpSearchServiceTest {
  @Test
  public void askForClientConfigLocationWhenUriIsNullShouldReturnEmptyString() throws Exception {
    HttpSearchService httpSearchService = new HttpSearchService();
    Assert.assertTrue( httpSearchService.askForClientsConfigLocation( null ).isEmpty() );
  }

  @SuppressWarnings( "unchecked" )
  @Test( expected = ServiceException.class )
  public void searchForConfigsLocationWhenCommonUtilRaiseExceptionShouldRaiseServiceException() throws Exception {
    HttpSearchService httpSearchService = new HttpSearchService();
    HttpCommonUtil httpCommonUtil = Mockito.mock( HttpCommonUtil.class );
    SearchStrategy searchStrategy = Mockito.mock( SearchStrategy.class );
    PowerMockito.mockStatic( CommonUtilHolder.class );

    Mockito.when( CommonUtilHolder.httpCommonUtilInstance() ).thenReturn( httpCommonUtil );
    Mockito.when( httpCommonUtil.createHttpClient() ).thenThrow( CommonUtilException.class );
    Mockito.when( searchStrategy.getStrategyCommand( Mockito.anyList() ) ).thenReturn( StringUtils.EMPTY );

    httpSearchService.searchForConfigsLocation( StringUtils.EMPTY, Collections.emptyList(), searchStrategy );
  }

  @SuppressWarnings( "unchecked" )
  @Test( expected = ServiceException.class )
  public void searchForConfigsLocationWhenAskForConfigLocationReturnEmptyStringShouldRaiseServiceException()
    throws Exception {
    HttpSearchService httpSearchService = Mockito.mock( HttpSearchService.class );
    HdpSearchStrategy searchStrategy = new HdpSearchStrategy();

    Mockito.when( httpSearchService.askForClientsConfigLocation( Mockito.anyString() ) )
      .thenReturn( StringUtils.EMPTY );
    Mockito.when( httpSearchService
      .searchForConfigsLocation( Mockito.anyString(), Mockito.anyList(), Mockito.any( SearchStrategy.class ) ) )
      .thenCallRealMethod();

    httpSearchService.searchForConfigsLocation( StringUtils.EMPTY, Collections.emptyList(), searchStrategy );
  }

  @SuppressWarnings( "unchecked" )
  @Test
  public void searchForConfigsLocationWhenStrategyReturnNotEmptyStringShouldModifyDownloadableFileList()
    throws Exception {
    HttpSearchService httpSearchService = Mockito.mock( HttpSearchService.class );
    SearchStrategy searchStrategy = Mockito.mock( SearchStrategy.class );
    String remoteUrl = "test";
    List<DownloadableFile> downloadableFiles = new ArrayList<>();
    downloadableFiles.add( new DownloadableFile( "test", Collections.emptyList() ) );

    Mockito.when( httpSearchService.askForClientsConfigLocation( Mockito.anyString() ) )
      .thenReturn( StringUtils.EMPTY );
    Mockito.when( httpSearchService
      .searchForConfigsLocation( Mockito.anyString(), Mockito.anyList(), Mockito.any( SearchStrategy.class ) ) )
      .thenCallRealMethod();
    Mockito.when( searchStrategy.tryToResolveCommandResult( Mockito.anyString(), Mockito.anyList() ) )
      .thenReturn( downloadableFiles );

    Assert.assertEquals( HttpSearchService.HTTP_PREFIX + remoteUrl,
      httpSearchService.searchForConfigsLocation( remoteUrl, downloadableFiles, searchStrategy ).get( 0 )
        .getDownloadPath() );

  }

}