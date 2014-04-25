package org.exoplatform.addons.messaging.portlet.message;

import juzu.*;
import org.exoplatform.addons.messaging.bean.StatisticBean;
import org.exoplatform.commons.juzu.ajax.Ajax;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Created by menzli on 10/04/14.
 */
@SessionScoped
public class MessageApplication {

    private static Log log = ExoLogger.getLogger(MessageApplication.class);

    static Set<String> locations = new HashSet<String>();
    static {
        locations.add("marseille");
        locations.add("paris");
    }
    @Inject
    @Path("index.gtmpl")
    org.exoplatform.addons.messaging.portlet.message.templates.index index;

    @Inject
    @Path("fragment.gtmpl")
    org.exoplatform.addons.messaging.portlet.message.templates.fragment fragment;

    @View
    public void index() {
        index("marseille");
    }

    @View
    public void index(String location) {
        index.
                with().
                location(location).
                temperature("50").
                locations(locations).
                render();
    }

    @Action
    public Response add(String location) {
        if (!locations.contains(location)) {
            locations.add(location);
        }
        return MessageApplication_.index(location);
    }

    @Ajax
    @Resource
    public void getFragment(String location) {
        fragment.
                with().
                location(location).
                temperature("600").
                render();
    }
    @Ajax
    @juzu.Resource
    @Route("/statistics")
    public Response getStatistics() {
        List<StatisticBean> statisticBeanList;
        try {
            // statisticBeanList = statisticsService.getAllStats();
            statisticBeanList = getAllStats();
        } catch (Exception e) {
            log.error("Error while fetching synchronization target servers", e);
            statisticBeanList = new ArrayList<StatisticBean>();
        }

        StringBuilder jsonServers = new StringBuilder(50);
        jsonServers.append("{\"statisticBeanList\":[");
        for(StatisticBean statisticBean : statisticBeanList) {
            jsonServers.append("{\"id\":\"")
                    .append(statisticBean.getId())
                    .append("\",\"category\":\"")
                    .append(statisticBean.getCategory())
                    .append("\",\"categoryId\":\"")
                    .append(statisticBean.getCategoryId())
                    .append("\",\"content\":\"")
                    .append(statisticBean.getContent())
                    .append("\",\"username\":\"")
                    .append(statisticBean.getUsername())
                    .append("\",\"url\":\"")
                    .append(statisticBean.getUrl())
                    .append("\",\"active\":")
                    .append(statisticBean.isActive())
                    .append("},");
        }
        if(!statisticBeanList.isEmpty()) {
            jsonServers.deleteCharAt(jsonServers.length()-1);
        }
        jsonServers.append("]}");

        return Response.ok(jsonServers.toString());
    }
    private static List<StatisticBean> getAllStats () {
        List<StatisticBean> statisticBeanList = new ArrayList<StatisticBean>();

        StatisticBean statisticBean = new StatisticBean ("1","catA","catA_1","Puzzle","khemais","explatform.com",true);

        statisticBeanList.add(statisticBean);

        statisticBean = new StatisticBean ("2","catB","catB_1","Lopo","Esslem","google.com",true);

        statisticBeanList.add(statisticBean);

        statisticBean = new StatisticBean ("3","catC","catC_1","Logo","BnjP","community.com",true);

        statisticBeanList.add(statisticBean);

        return statisticBeanList;
    }

}


