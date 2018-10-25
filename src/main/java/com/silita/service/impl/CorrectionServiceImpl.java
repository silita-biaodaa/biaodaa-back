package com.silita.service.impl;

import com.silita.biaodaa.elastic.common.ConstantUtil;
import com.silita.biaodaa.elastic.common.NativeElasticSearchUtils;
import com.silita.biaodaa.elastic.model.PaginationAndSort;
import com.silita.biaodaa.elastic.model.QuerysModel;
import com.silita.commons.elasticSearch.InitESClient;
import com.silita.dao2.*;
import com.silita.model.*;
import com.silita.service.ICorrectionService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.oldProjectUtils.CommonUtil;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-18 14:26
 */
@Service("correctionService")
public class CorrectionServiceImpl extends AbstractService implements ICorrectionService {

    @Autowired
    private AllZhMapper allZhMapper;
    @Autowired
    private AptitudeDictionaryMapper aptitudeDictionaryMapper;

    @Autowired
    private SnatchurlMapper snatchurlMapper;
    @Autowired
    private ZhaobiaoDetailOthersMapper zhaobiaoDetailOthersMapper;
    @Autowired
    private ZhongbiaoDetailOthersMapper zhongbiaoDetailOthersMapper;
    @Autowired
    private SnatchUrlCertMapper snatchUrlCertMapper;
    @Autowired
    private SnatchurlcontentMapper snatchurlcontentMapper;

    @Autowired
    private NativeElasticSearchUtils nativeElasticSearchUtils;

    @Override
    public List<AllZh> ListAllZhByName(AllZh allZh) {
        return allZhMapper.listAllZhByName(allZh);
    }

    @Override
    public Map<String, Object> listSnatchurl(Snatchurl snatchurl) {
        if(!"hunan".equals(snatchurl.getSource())) {
            Map result = new HashMap<String, Object>();
            result.put("datas", snatchurlMapper.listSnatchurl(snatchurl));
            result.put("total", snatchurlMapper.countSnatchurl(snatchurl));
            return super.handlePageCount(result, snatchurl);
        }
        return null;
    }

    @Override
    public Integer updateSnatchurlIsShowById(Snatchurl snatchurl) {
        return snatchurlMapper.updateSnatchurlIsShowById(snatchurl);
    }

    @Override
    public List<ZhaobiaoDetailOthers> listZhaobiaoDetailBySnatchUrlId(ZhaobiaoDetailOthers zhaobiaoDetailOthers) {
        return zhaobiaoDetailOthersMapper.listZhaobiaoDetailOthersBySnatchUrlId(zhaobiaoDetailOthers);
    }

    @Override
    public Integer updateZhaobiaoDetailById(ZhaobiaoDetailOthers zhaobiaoDetailOthers) {
        SnatchUrlCert delSnatchUrlCert = new SnatchUrlCert();
        delSnatchUrlCert.setContId(zhaobiaoDetailOthers.getSnatchUrlId());
        delSnatchUrlCert.setSource(zhaobiaoDetailOthers.getSource());
        //删除公告全部资质
        snatchUrlCertMapper.deleteSnatchUrlCertByContId(delSnatchUrlCert);
        List<SnatchUrlCert> snatchUrlCerts = zhaobiaoDetailOthers.getSnatchUrlCerts();
        if(snatchUrlCerts.size() > 0) {
            List<SnatchUrlCert> insertSnatchUrlCerts = new ArrayList<>(snatchUrlCerts.size());
            for (int i = 0; i < snatchUrlCerts.size(); i++) {
                SnatchUrlCert snatchUrlCert = snatchUrlCerts.get(i);
                if(StringUtils.isEmpty(snatchUrlCert.getType())) {
                    snatchUrlCert.setType("OR");
                }
                snatchUrlCert.setLicence("yes");
                snatchUrlCert.setSource(zhaobiaoDetailOthers.getSource());
                snatchUrlCert.setBlock(zhaobiaoDetailOthers.getBlock());
                String finalUuid = snatchUrlCert.getFinalUuid();
                String mainUuid = finalUuid.substring(0, finalUuid.indexOf("/"));
                String rank = finalUuid.substring(finalUuid.indexOf("/") + 1);
                AptitudeDictionary aptitudeDictionary = aptitudeDictionaryMapper.getAptitudeDictionaryByMajorUUid(mainUuid);
                snatchUrlCert.setCertificate(aptitudeDictionary.getMajorName() + CommonUtil.spellRank(rank));
                snatchUrlCert.setCertificateUuid(CommonUtil.spellUuid(mainUuid, rank));
                insertSnatchUrlCerts.add(snatchUrlCert);
            }
            if(insertSnatchUrlCerts.size() > 0) {
                snatchUrlCertMapper.batchInsertSnatchUrlCert(insertSnatchUrlCerts);
            }
        }
        //修改招标编辑明细
        zhaobiaoDetailOthersMapper.updateZhaobiaoDetailOthersById(zhaobiaoDetailOthers);
        Snatchurl snatchurl = new Snatchurl();
        snatchurl.setTitle(zhaobiaoDetailOthers.getProjName());
        snatchurl.setSource(zhaobiaoDetailOthers.getSource());
        snatchurl.setId(zhaobiaoDetailOthers.getId());
        snatchurl.setType(0);
        //修改公告主表
        snatchurlMapper.updateSnatchurlById(snatchurl);
        return null;
    }

    @Override
    public List<Map<String, Object>> listCompanyByNameOrPinYin(String queryKey) {
        List lists = new ArrayList<TbCompany>(10);
        TransportClient client = InitESClient.getInit();
        Map sort = new HashMap<String, String>();
        sort.put("px", SortOrder.DESC);
        PaginationAndSort pageSort = new PaginationAndSort(1, 10, sort);

        List<QuerysModel> querys = new ArrayList();
        if (!StringUtils.isEmpty(queryKey)) {
            queryKey = queryKey.toLowerCase();
            querys.add(new QuerysModel(ConstantUtil.CONDITION_SHOULD, ConstantUtil.MATCHING_WILDCARD, "comName", "*" + queryKey + "*"));
            querys.add(new QuerysModel(ConstantUtil.CONDITION_SHOULD, ConstantUtil.MATCHING_WILDCARD, "comNamePy", "*" + queryKey + "*"));
        }
        SearchResponse response = nativeElasticSearchUtils.complexQuery(client, "company", "comes", querys, null, ConstantUtil.CONDITION_MUST, pageSort);
        Map temp;
        for (SearchHit hit : response.getHits()) {
            temp = new HashMap(4);
            temp.put("comId", hit.getSource().get("comId"));
            temp.put("companyName", hit.getSource().get("comName"));
            temp.put("creditCode", hit.getSource().get("creditCode"));
            lists.add(temp);
        }
        return lists;
    }

    @Override
    public List<ZhongbiaoDetailOthers> listZhongbiaoDetailBySnatchUrlId(ZhongbiaoDetailOthers zhongbiaoDetailOthers) {
        return zhongbiaoDetailOthersMapper.listZhongbiaoDetailOthersBySnatchUrlId(zhongbiaoDetailOthers);
    }

    @Override
    public Integer updateZhongbiaoDetailById(ZhongbiaoDetailOthers zhongbiaoDetailOthers) {
        //修改中标编辑明细
        zhongbiaoDetailOthersMapper.updateZhongbiaoDetailOthersById(zhongbiaoDetailOthers);
        Snatchurl snatchurl = new Snatchurl();
        snatchurl.setTitle(zhongbiaoDetailOthers.getProjName());
        snatchurl.setSource(zhongbiaoDetailOthers.getSource());
        snatchurl.setId(zhongbiaoDetailOthers.getId());
        snatchurl.setType(2);
        //修改公告主表
        snatchurlMapper.updateSnatchurlById(snatchurl);
        return null;
    }

    @Override
    public Snatchurlcontent getSnatchurlcontentBySnatchUrlId(Snatchurlcontent snatchurlcontent) {
        return snatchurlcontentMapper.getSnatchurlcontentBySnatchUrlId(snatchurlcontent);
    }

}
