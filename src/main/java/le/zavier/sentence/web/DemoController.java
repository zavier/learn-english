package le.zavier.sentence.web;

import le.zavier.sentence.mapper.SentenceMapper;
import le.zavier.sentence.model.Sentence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DemoController {

    private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private SentenceMapper mapper;

    @RequestMapping(value = "index", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
//        Sentence sentence = new Sentence("由你决定", "It's up to you");
//        int insert = mapper.insert(sentence);
//        logger.info("insert {}", insert);
        Sentence sentence = mapper.selectByPrimaryKey(1);
        logger.info("search result : {}", sentence.toString());
        return "Hello";
    }
}
