package com.daydao.dubbox.rest.service.impl;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.alibaba.fastjson.JSON;
import com.daydao.dubbox.rest.bean.Person;
import com.daydao.dubbox.rest.resp.RetBean;
import com.daydao.dubbox.rest.service.IPersonService;
import com.daydao.dubbox.rest.util.RespUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;

/**
 * PersonService
 * @author Chenpz
 * @package com.daydao.dubbox.rest.service.impl
 * @date 2017/1/6/16:50
 */
@Service
@Path("person")
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public class PersonService implements IPersonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    @POST
    @Path("addPerson")
    @Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    @Override
    public RetBean addPerson(Person person) {
        LOGGER.info("called addPerson:{} ", JSON.toJSONString(person, true));
        return RespUtils.createSuccRespData();
    }

    @GET
    @Path("delPerson/{personId:\\d+}")
    @Override
    public RetBean delPerson(@PathParam("personId")Long personId) {
        LOGGER.info("called delPerson:{}", personId);
        return RespUtils.createErrorRespData("delete error");
    }


    @GET
    @Path("delPerson/{corpId:\\d+}/{personId:\\d+}")
    @Override
    public RetBean delPerson(@PathParam("personId")Long corpId, @PathParam("personId")Long personId) {
        LOGGER.info("called delPerson:{}", personId);
        return RespUtils.createErrorRespDataWithCode(1001, "delete error");
    }


    @POST
    @Path("updatePerson")
    @Consumes({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
    @Override
    public RetBean updatePerson(Person person) {
        LOGGER.info("called updatePerson:{} ", JSON.toJSONString(person, true));
        return RespUtils.createSuccRespDataWithMsg("update successful!");
    }


    @GET
    @Path("getPerson/{personId:\\d+}")
    @Override
    public RetBean getPerson(@PathParam("personId") Long personId) {

        LOGGER.info("called getPerson:{}", personId);
        return RespUtils.createRespData(Person.builder()
                .personId(personId)
                .personName("张三")
                .sex("男")
                .age(25).build());
    }
}
