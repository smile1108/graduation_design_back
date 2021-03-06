package com.jiac.user.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.common.dto.FollowUserDto;
import com.jiac.common.entity.User;
import com.jiac.common.entity.UserFollow;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.dto.UserDto;
import com.jiac.common.vo.PageVo;
import com.jiac.user.feign.ArticleFeign;
import com.jiac.user.repository.UserFollowRepository;
import com.jiac.user.repository.UserRepository;
import com.jiac.user.request.*;
import com.jiac.user.service.UserService;
import com.jiac.user.utils.SHA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * FileName: UserServiceImpl
 * Author: Jiac
 * Date: 2022/2/2 14:17
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFollowRepository userFollowRepository;

    @Autowired
    private ArticleFeign articleFeign;

    // nginx静态图片目录
    @Value("${nginx-static-dir}")
    private String NGINX_STATIC_DIR;

    @Override
    public UserDto login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        // 登录验证比较密码时 再次通过SHA工具类加密 与 数据库进行比较
        if(user == null || !user.getPassword().equals(SHA.getResult(request.getPassword()))) {
            throw new MyException(ErrorEnum.PASSWORD_WRONG);
        }
        return UserDto.of(user);
    }

    @Override
    public UserDto register(UserRegisterRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        // 首先判断用户是否已经存在(学号相同)
        if(user != null) {
            throw new MyException(ErrorEnum.USER_IS_EXIST);
        }
        User userByEmail = userRepository.findByEmail(request.getEmail());
        // 判断邮箱是否已经被注册
        if(userByEmail != null) {
            throw new MyException(ErrorEnum.EMAIL_IS_EXIST);
        }
        user = new User();
        user.setUsername(request.getUsername());
        // 数据库中设置密码为加密之后密码 通过SHA工具类进行加密
        user.setPassword(SHA.getResult(request.getPassword()));
        user.setEmail(request.getEmail());
        RandomUtil randomUtil = new RandomUtil();
        // 注册时 随机给一个nickname
        user.setNickname(randomUtil.randomString(20));
        // 注册时 没有设置头像 给一个默认头像
        user.setProfile("default.png");
        // 写数据库
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
    public Integer countFollowed(String followUsername) {
        User user = userRepository.findByUsername(followUsername);
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        return userFollowRepository.countFollowed(followUsername);
    }

    @Override
    public PageVo<FollowUserDto> getFollowList(GetFollowListRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "followDate");
        int page = request.getPage();
        int pageSize = request.getPageSize();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        Specification<UserFollow> specification = (Specification<UserFollow>) (root, query, cb) -> cb.equal(root.get("username"), request.getUsername());
        Page<UserFollow> useFollowPage = userFollowRepository.findAll(specification, pageRequest);
        return transferUserFollowPage2UserDtoPageVo(useFollowPage);
    }

    @Override
    public UserDto modifyPassword(ModifyPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        if(!user.getPassword().equals(SHA.getResult(request.getOldPassword()))) {
            throw new MyException(ErrorEnum.PASSWORD_WRONG);
        }
        user.setPassword(SHA.getResult(request.getNewPassword()));
        User save = userRepository.save(user);
        return UserDto.of(save);
    }

    @Override
    public UserDto findPassword(FindPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername());
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        if(!user.getEmail().equals(request.getEmail())) {
            throw new MyException(ErrorEnum.EMAIL_BIND_WRONG);
        }
        return UserDto.of(user);
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

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new MyException(ErrorEnum.USER_NOT_EXIST);
        }
        return UserDto.of(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword, String username) {
        List<User> userList = userRepository.searchUser("%" + keyword + "%", username);
        return userList.stream().map(UserDto::of).collect(Collectors.toList());
    }

    private PageVo<FollowUserDto> transferUserFollowPage2UserDtoPageVo(Page<UserFollow> userFollowPage) {
        List<FollowUserDto> followUserDtoList = userFollowPage.stream().map((userFollow -> {
            User user = userRepository.findByUsername(userFollow.getFollowUsername());
            FollowUserDto followUserDto = FollowUserDto.of(user);
            followUserDto.setFollowSum(userFollowRepository.countFollowed(userFollow.getFollowUsername()));
            followUserDto.setArticleSum(articleFeign.countArticle(userFollow.getFollowUsername()).getData());
            return followUserDto;
        })).collect(Collectors.toList());
        PageVo<FollowUserDto> followUserDtoPageVo = new PageVo<>();
        followUserDtoPageVo.setLists(followUserDtoList);
        followUserDtoPageVo.setSumPage(userFollowPage.getTotalPages());
        followUserDtoPageVo.setCount(userFollowPage.getTotalElements());
        return followUserDtoPageVo;
    }
}
