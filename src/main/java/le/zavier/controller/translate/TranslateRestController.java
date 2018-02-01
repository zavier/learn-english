package le.zavier.controller.translate;

import le.zavier.commons.ResultBean;
import le.zavier.pojo.TransResult;
import le.zavier.service.TranslateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/translate")
public class TranslateRestController {
    private static final Logger logger = LoggerFactory.getLogger(TranslateRestController.class);

    @Autowired
    private TranslateService translateService;

    @PostMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean api(@RequestParam(value = "query") String query,
        @RequestParam(value = "from", defaultValue = "auto") String from,
        @RequestParam(value = "to", defaultValue = "en") String to) {
        TransResult translate = translateService.translate(query, from, to);
        return ResultBean.createBySuccess(translate);
    }
}
