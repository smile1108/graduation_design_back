package com.jiac.user.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.common.entity.User;
import com.jiac.common.entity.UserFollow;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.dto.UserDto;
import com.jiac.user.repository.UserFollowRepository;
import com.jiac.user.repository.UserRepository;
import com.jiac.user.request.UserLoginRequest;
import com.jiac.user.request.UserModifyMessageRequest;
import com.jiac.user.request.UserRegisterRequest;
import com.jiac.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;


/**
 * FileName: UserServiceImpl
 * Author: Jiac
 * Date: 2022/2/2 14:17
 */
@Service
public class
UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    // nginx静态图片目录
    private final String NGINX_STATIC_DIR = "E:\\nginx-1.20.2\\html\\images\\";

    @Override
    public UserDto login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null || !user.getPassword().equals(request.getPassword())) {
            throw new MyException(ErrorEnum.PASSWORD_WRONG);
        }
        return UserDto.of(user);
    }

    @Override
    public UserDto register(UserRegisterRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user != null) {
            throw new MyException(ErrorEnum.USER_IS_EXIST);
        }
        user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        RandomUtil randomUtil = new RandomUtil();
        // 注册时 随机给一个nickname
        user.setNickname(randomUtil.randomString(20));
        // 注册时 没有设置头像 给一个默认头像
        user.setProfile("default.png");
        userRepository.save(user);

        return UserDto.of(user);
    }

    @Override
    public UserDto uploadAvatar(String username, MultipartFile file) throws IOException {
        // 先根据传递过来的username检查该用户是否存在
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        // 如果用户存在 再继续后续的逻辑 将头像文件上传到nginx静态资源目录 更新数据库
        // 先放入静态目录中
        RandomUtil randomUtil = new RandomUtil();
        // 获取原来文件的类型 比如 jpg  png
        System.out.println(file.getOriginalFilename());
        String[] split = file.getOriginalFilename().split("\\.");
        String suffix = split.length == 1 ? "jpg" : split[1];
        String randomFileName = randomUtil.randomString(20) + "." + suffix;
        // 根据随机生成的10位字符串 拼接上Nginx的静态目录 组成文件存储的路径
        String filePath = NGINX_STATIC_DIR + randomFileName;
        // 将文件写入对应路径中
        FileUtil.writeBytes(file.getBytes(), filePath);

        // 更新数据库中的头像路径
        user.setProfile(randomFileName);
        User save = userRepository.save(user);
        return UserDto.of(save);
    }

    @Override
    public UserDto modifyMessage(UserModifyMessageRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        // 如果用户存在 再修改对应的信息
        user.setNickname(request.getNickname());
        user.setSchool(request.getSchool());
        user.setCollege(request.getCollege());
        user.setSpecialty(request.getSpecialty());
        user.setGender(request.getGender());
        user.setResume(request.getResume());
        User save = userRepository.save(user);
        return UserDto.of(save);
    }

    @Override
    public Boolean userExist(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean follow(String username, String followUsername) {
        User user = userRepository.findByUsername(username);
        User followUser = userRepository.findByUsername(followUsername);
        if(user == null || followUser == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        UserFollow userFollow = userFollowRepository.getUserFollowByUsernameAndFollowUsername(username, followUsername);
        if(userFollow != null) {
            throw new MyException(ErrorEnum.DO_NOT_DONE_AGAIN);
        }
        userFollow = new UserFollow();
        userFollow.setUsername(username);
        userFollow.setFollowUsername(followUsername);
        userFollow.setFollowDate(new Date());
        userFollowRepository.save(userFollow);
        return true;
    }

    @Override
    public Boolean unfollow(String username, String followUsername) {
        User user = userRepository.findByUsername(username);
        User followUser = userRepository.findByUsername(followUsername);
        if(user == null || followUser == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        UserFollow userFollow = userFollowRepository.getUserFollowByUsernameAndFollowUsername(username, followUsername);
        if(userFollow == null) {
            throw new MyException(ErrorEnum.ILLEGAL_OPERATION);
        }
        userFollowRepository.delete(userFollow);
        return true;
    }

    @Override
    public Integer countFollow(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        return userFollowRepository.countFollow(username);
    }

    @Override
    public Boolean getUserFollow(String username, String articleAuthor) {
        User user = userRepository.findByUsername(username);
        User author = userRepository.findByUsername(articleAuthor);
        if(user == null || author == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        UserFollow userFollow = userFollowRepository.getUserFollowByUsernameAndFollowUsername(username, articleAuthor);
        return (userFollow != null);
    }
}
