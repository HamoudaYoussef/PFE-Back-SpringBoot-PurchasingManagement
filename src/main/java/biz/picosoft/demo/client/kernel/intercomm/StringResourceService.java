package biz.picosoft.demo.client.kernel.intercomm;

import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.client.kernel.model.global.Language;
import biz.picosoft.demo.client.kernel.model.global.StringResourceDTO;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@Service
public class StringResourceService {

    public static HashMap<String, String> arabe = new HashMap<String, String>();
    public static HashMap<String, String> francais = new HashMap<String, String>();
    public static HashMap<String, String> anglais = new HashMap<String, String>();

    private final Logger log = LoggerFactory.getLogger(StringResourceService.class);
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private KernelInterface kernelInterface;
//    @Autowired
//    private Configuration freemarkerConfig;

    ObjectMapper translatemapper;

    @Bean
    public void initTranslateMapper() {
        translatemapper = new ObjectMapper().registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule()).setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        translatemapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        translatemapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        translatemapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        translatemapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Object stringRessourceTrd(Object data) {
        if (data == null) return data;

        long timeInitial = System.currentTimeMillis();
        String l = currentUser.getStringResTrad();
        HashMap<String, String> defaultL = (l == null || l.equals(Language.AN)) ? anglais :
                (l.equals(Language.AR)) ? arabe :
                        (l.equals(Language.FR)) ? francais : anglais;


        JsonNode jsonNode = translatemapper.valueToTree(data);

        traduireJsonNode(jsonNode, defaultL);
        //long duration = System.currentTimeMillis()-timeInitial;
        Object o = translatemapper.convertValue(jsonNode, Object.class);
        return o;
    }

    private void traduireJsonNode(JsonNode jsonNode, HashMap<String, String> traductions) {
        Boolean isArray = jsonNode.isArray();
        Boolean isObject = jsonNode.isObject();
        if (isArray) {
            jsonNode.forEach(node -> {
                traduireJsonNode(node, traductions);
            });
        } else if (isObject) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            objectNode.fields().forEachRemaining(entry -> {
                JsonNode valueNode = entry.getValue();
                if (valueNode.isTextual()) {
                    String traduit = null;
                    try {
                        traduit = traduireTexte(valueNode.asText(), traductions);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (TemplateException e) {
                        throw new RuntimeException(e);
                    }
                    objectNode.put(entry.getKey(), traduit);
                } else {
                    traduireJsonNode(valueNode, traductions);
                }
            });
        }
    }

    private String traduireTexte(String texte, HashMap<String, String> traductions) throws IOException, TemplateException {
//        Template t = new Template(null, texte, freemarkerConfig);
//        return FreeMarkerTemplateUtils.processTemplateIntoString(t, traductions);
        if ((texte != null) && (texte.startsWith("$>>"))) {
            if (traductions.get(texte) != null)
                return traductions.get(texte);

        }
        return texte;
    }


    @Scheduled(fixedDelay = 43200000)
    @Bean
    public void importStringRes() {
        try {
            List<StringResourceDTO> stringResourceDTOS = kernelInterface.getAllStringResources();
            for (int i = 0; i < stringResourceDTOS.size(); i++) {
                arabe.put(stringResourceDTOS.get(i).getKey(), stringResourceDTOS.get(i).getArabic());
                francais.put(stringResourceDTOS.get(i).getKey(), stringResourceDTOS.get(i).getFrench());
                anglais.put(stringResourceDTOS.get(i).getKey(), stringResourceDTOS.get(i).getEnglish());
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

}
