package le.zavier.service;

import java.util.UUID;
import le.zavier.config.RootConfig;
import le.zavier.pojo.Knowledge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = RootConfig.class)
public class KnowledgeServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeServiceTest.class);
    private Knowledge knowledge = new Knowledge();

    @Autowired
    private IKnowledgeService iKnowledgeService;

    private static Long id = 1000000L;

    @Before
    public void setUp() {
        knowledge.setId(id);
        knowledge.setChinese(UUID.randomUUID().toString());
        knowledge.setEnglish(UUID.randomUUID().toString());
        knowledge.setType((short)2);

        id += 1;
        Knowledge result = iKnowledgeService.addKnowledge(this.knowledge);
        Assert.assertEquals(result.toString(), knowledge.toString());
    }

    @Test
    public void testUpdateKnowledge() {
        knowledge.setChinese("这个由你决定");
        Knowledge result = iKnowledgeService.updateKnowledge(this.knowledge);
        Assert.assertEquals(result.toString(), knowledge.toString());
    }

    @Test
    public void testGetKnowledgeById() {
        logger.info("GET ID : " + knowledge.getId());
        Knowledge result = iKnowledgeService.getKnowledgeById(1L);
        logger.info(result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void testRemoveKnowledgeById() {
        int i = iKnowledgeService.removeKnowledgeById(knowledge.getId());
        Assert.assertEquals(1, i);
    }
}
