package le.zavier.sentence.repository;

import le.zavier.configs.AopConfig;
import le.zavier.configs.RootConfig;
import le.zavier.configs.WebConfig;
import le.zavier.sentence.mapper.SentenceMapper;
import le.zavier.sentence.model.Sentence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class})
public class SentenceRepositoryTest {

    @Autowired
    private SentenceMapper sentenceMapper;

    @Test
    public void testSaveSentence() {
        Sentence sentence = sentenceMapper.selectByPrimaryKey(1);
        Assert.assertEquals(sentence.getChinese(), "由你决定");
        Assert.assertEquals(sentence.getEnglish(), "It's up to you");
    }
}
