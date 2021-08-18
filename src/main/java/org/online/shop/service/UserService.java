package org.online.shop.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public static List<String> getAuthorityList() {
        List<String> authorityList = new ArrayList<>();
        authorityList.add("USER");
        authorityList.add("ADMIN");
        return authorityList;
    }
}
