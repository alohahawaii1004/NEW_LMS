package com.vertexid.spring.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <b>Description</b>
 * <pre>
 *     사용자 세션저장소
 *     동접관리 등으로 사용
 * </pre>
 */
public class SessionListener implements HttpSessionBindingListener {

    public static final String SIMPLE_PRINCIPAL = "SIMPLE_PRINCIPAL";
    /**
     * logger
     */
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    // 싱글톤 객체를 담을 변수
    private static SessionListener sessionListener = new SessionListener();

    // 로그인한 접속자를 저장한 HashTable (데이터를 해시하여 테이블 내의 주소를 계산하고 데이터를 담는 것 , 해시함수 알고리즘은 나눗셈 법. 자릿수 접기 등등)
    @SuppressWarnings("rawtypes")
    private static final Hashtable loginUsers = new Hashtable();

    // 싱글톤 처리
    public static synchronized SessionListener getInstance() {
        if (sessionListener == null) {
            sessionListener = new SessionListener();
        }
        return sessionListener;
    }

    // 세션이 연결시 호출 (해시테이블에 접속자 저장)
    // setAttribute 시에 호출
    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        loginUsers.put(event.getSession(), event.getName());
        log.info(event.getName() + " 로그인 완료");
        log.info("현재 접속자 수 : " + getUserCount());
    }

    // 세션이 끊겼을시 호출
    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        loginUsers.remove(session);
        log.info(event.getName() + " 로그아웃 완료");
        log.info("현재 접속자 수 : " + getUserCount());
    }

    // 입력받은 아이디를 해시테이블에서 삭제
    public void removeSession(String userId) {
        Enumeration e = loginUsers.keys();
        HttpSession session = null;
        while (e.hasMoreElements()) {
            session = (HttpSession) e.nextElement();
            if (loginUsers.get(session).equals(userId)) {
                //세션이 invalidate 될때 HttpSessionBindingListener 를
                //구현하는 클레스의 valueUnbound()함수가 호출된다.
                session.invalidate();
            }
        }
    }

    // 입력받은 아이디를 해시테이블에서 삭제
    public HttpSession getSession(String userId) {
        Enumeration e = loginUsers.keys();
        HttpSession session = null;
        HttpSession rtnSesion = null;
        while (e.hasMoreElements()) {
            session = (HttpSession) e.nextElement();
            if (loginUsers.get(session).equals(userId)) {
                rtnSesion = session;
            }
        }
        return rtnSesion;
    }

    /**
     * 해당 아이디의 동시 사용을 막기위해서
     * 이미 사용중인 아이디인지를 확인한다.
     *
     * @param userId 사용자 ID
     * @return true: 존재함, other: 존재하지 않음
     */
    public boolean isUsing(String userId) {
        return loginUsers.containsValue(userId);
    }

    /**
     * 로그인을 완료한 사용자의 아이디를 세션에 저장하는 메소드
     *
     * @param session session Object
     * @param userId 사용자 ID
     */
    public void setSession(HttpSession session, String userId) {
        // HACK 이순간에 Session Binding 이벤트가 일어나는 시점
        //name 값으로 userId, value 값으로 자기자신(HttpSessionBindingListener 를 구현하는 Object)
        session.setAttribute(userId, this);// login 에 자기자신을 집어넣는다.
    }

    /**
     * 입력받은 세션 Object 로 아이디를 리턴한다.
     * @param session 접속한 사용자의 session Object
     * @return 접속자 아이디
     */
    public String getUserID(HttpSession session) {
        return (String) loginUsers.get(session);
    }


    /**
     * 현재 접속한 총 사용자 수
     * @return int  현재 접속자 수
     */
    public int getUserCount() {
        return loginUsers.size();
    }


    /**
     * 현재 접속중인 모든 사용자 아이디를 출력
     */
    public void printLoginUsers() {
        Enumeration e = loginUsers.keys();
        HttpSession session = null;
        log.info("===========================================");
        int i = 0;
        while (e.hasMoreElements()) {
            session = (HttpSession) e.nextElement();
            log.info((++i) + ". 접속자 : " + loginUsers.get(session));
        }
        log.info("===========================================");
    }

    /**
     * 현재 접속중인 모든 사용자리스트를 리턴
     * @return list
     */
    public Collection getUsers() {
        Collection collection = loginUsers.values();
        return collection;
    }
}
