package le.zavier.controller.translate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/translate")
public class TranslateController {
    @GetMapping("/translate")
    public String translatePage() {
        return "/translate/translate";
    }
}
