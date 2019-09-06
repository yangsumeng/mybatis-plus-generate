
import env from '../../build/env';
import semver from 'semver';
import packjson from '../../package.json';
import lazyLoading from './lazyLoading.js';
import router from '@/router/index';
import {getRequest} from "../utils/api";
import Cookies from "js-cookie";

let util = {

};
util.title = function (title) {
    title = title || '龙泉专题配置系统';
    window.document.title = title;
};


util.inOf = function (arr, targetArr) {
    let res = true;
    arr.forEach(item => {
        if (targetArr.indexOf(item) < 0) {
            res = false;
        }
    });
    return res;
};

util.oneOf = function (ele, targetArr) {
    if (targetArr.indexOf(ele) >= 0) {
        return true;
    } else {
        return false;
    }
};

util.getRouterObjByName = function (routers, name) {
    if (!name || !routers || !routers.length) {
        return null;
    }
    let routerObj = null;
    for (let item of routers) {
        if (item.name === name) {
            return item;
        }
        routerObj = util.getRouterObjByName(item.children, name);
        if (routerObj) {
            return routerObj;
        }
    }
    return null;
};

util.handleTitle = function (vm, item) {
    if (typeof item.title === 'object') {
        return vm.$t(item.title.i18n);
    } else {
        return item.title;
    }
};

util.setCurrentPath = function (vm, name) {
    let title = '';
    let isOtherRouter = false;
    vm.$store.state.app.routers.forEach(item => {
        if (item.children.length === 1) {
            if (item.children[0].name === name) {
                title = util.handleTitle(vm, item);
                if (item.name === 'otherRouter') {
                    isOtherRouter = true;
                }
            }
        } else {
            item.children.forEach(child => {
                if (child.name === name) {
                    title = util.handleTitle(vm, child);
                    if (item.name === 'otherRouter') {
                        isOtherRouter = true;
                    }
                }
            });
        }
    });
    let currentPathArr = [];
    if (name === 'home_index') {
        currentPathArr = [
            {
                title: util.handleTitle(vm, util.getRouterObjByName(vm.$store.state.app.routers, 'home_index')),
                path: '',
                name: 'home_index'
            }
        ];
    } else if ((name.indexOf('_index') >= 0 || isOtherRouter) && name !== 'home_index') {
        currentPathArr = [
            {
                title: util.handleTitle(vm, util.getRouterObjByName(vm.$store.state.app.routers, 'home_index')),
                path: '/home',
                name: 'home_index'
            },
            {
                title: title,
                path: '',
                name: name
            }
        ];
    } else {
        let currentPathObj = vm.$store.state.app.routers.filter(item => {
            if (item.children.length <= 1) {
                return item.children[0].name === name;
            } else {
                let i = 0;
                let childArr = item.children;
                let len = childArr.length;
                while (i < len) {
                    if (childArr[i].name === name) {
                        return true;
                    }
                    i++;
                }
                return false;
            }
        })[0];
        if (currentPathObj.children.length <= 1 && currentPathObj.name === 'home') {
            currentPathArr = [
                {
                    title: '首页',
                    path: '',
                    name: 'home_index'
                }
            ];
        } else if (currentPathObj.children.length <= 1 && currentPathObj.name !== 'home') {
            currentPathArr = [
                {
                    title: '首页',
                    path: '/home',
                    name: 'home_index'
                },
                {
                    title: currentPathObj.title,
                    path: '',
                    name: name
                }
            ];
        } else {
            let childObj = currentPathObj.children.filter((child) => {
                return child.name === name;
            })[0];
            currentPathArr = [
                {
                    title: '首页',
                    path: '/home',
                    name: 'home_index'
                },
                {
                    title: currentPathObj.title,
                    path: '',
                    name: currentPathObj.name
                },
                {
                    title: childObj.title,
                    path: currentPathObj.path + '/' + childObj.path,
                    name: name
                }
            ];
        }
    }
    vm.$store.commit('setCurrentPath', currentPathArr);

    return currentPathArr;
};

util.openNewPage = function (vm, name, argu, query) {
    let pageOpenedList = vm.$store.state.app.pageOpenedList;
    let openedPageLen = pageOpenedList.length;
    let i = 0;
    let tagHasOpened = false;
    while (i < openedPageLen) {
        if (name === pageOpenedList[i].name) { // 页面已经打开
            vm.$store.commit('pageOpenedList', {
                index: i,
                argu: argu,
                query: query
            });
            tagHasOpened = true;
            break;
        }
        i++;
    }
    if (!tagHasOpened) {
        let tag = vm.$store.state.app.tagsList.filter((item) => {
            if (item.children) {
                return name === item.children[0].name;
            } else {
                return name === item.name;
            }
        });
        tag = tag[0];
        if (tag) {
            tag = tag.children ? tag.children[0] : tag;
            if (argu) {
                tag.argu = argu;
            }
            if (query) {
                tag.query = query;
            }
            vm.$store.commit('increateTag', tag);
        }
    }
    vm.$store.commit('setCurrentPageName', name);
};

util.toDefaultPage = function (routers, name, route, next) {
    let len = routers.length;
    let i = 0;
    let notHandle = true;
    while (i < len) {
        if (routers[i].name === name && routers[i].children && routers[i].redirect === undefined) {
            route.replace({
                name: routers[i].children[0].name
            });
            notHandle = false;
            next();
            break;
        }
        i++;
    }
    if (notHandle) {
        next();
    }
};

util.fullscreenEvent = function (vm) {
    vm.$store.commit('initCachepage');
};

util.initRouter = function (vm) {
    const constRoutes = [];
    const otherRoutes = [];

    // 404路由需要和动态路由一起注入
    const otherRouter = [{
        path: '/*',
        name: 'error-404',
        meta: {
            title: '404-页面不存在'
        },
        component: 'error-page/404'
    }];


    // 判断用户是否登录
    let userInfo = Cookies.get('userInfo')
    if (userInfo === null || userInfo === "" || userInfo === undefined) {
        // 未登录
        return;
    }
    let userId = JSON.parse(Cookies.get("userInfo")).id;

    // 加载菜单
    getRequest("/permission/getMenuList/" + userId).then(res => {
        let menuData = res.result;
        if (menuData === null || menuData === "" || menuData === undefined) {
            return;
        }

        util.initRouterNode(constRoutes, menuData);
        util.initRouterNode(otherRoutes, otherRouter);
        // 添加主界面路由
        vm.$store.commit('updateAppRouter', constRoutes.filter(item => item.children.length > 0));
        // 添加全局路由
        vm.$store.commit('updateDefaultRouter', otherRoutes);
        // 刷新界面菜单
        vm.$store.commit('updateMenulist', constRoutes.filter(item => item.children.length > 0));

        let tagsList = [];
        vm.$store.state.app.routers.map((item) => {
            if (item.children.length <= 1) {
                tagsList.push(item.children[0]);
            } else {
                tagsList.push(...item.children);
            }
        });
        vm.$store.commit('setTagsList', tagsList);
    });
    // 判断用户是否登录
    //分模块权限
    let userInfoDic = JSON.parse(Cookies.get("userInfo"));
    if(userInfoDic.authorityType === '2')
    {
        // Cookies.set("marCpcodes",userInfoDic.marCpcode);
        Cookies.set("ndmsCpcodes",userInfoDic.ndmsCpcode);
        Cookies.set("tosCpcodes",userInfoDic.tosCpcode);
    }


};

// 生成路由节点
util.initRouterNode = function (routers, data) {
    for (var item of data) {
        let menu = Object.assign({}, item);
        // menu.components = import(`@/views/${menu.components}.vue`);
        menu.component = lazyLoading(menu.component);

        if (item.children && item.children.length > 0) {
            menu.children = [];
            util.initRouterNode(menu.children, item.children);
        }

        let meta = {};
        // 给页面添加权限和标题
        meta.permTypes = menu.permTypes ? menu.permTypes : null;
        meta.title = menu.title ? menu.title + " - 龙泉专题配置系统" : null;
        menu.meta = meta;
        routers.push(menu);
    }
};

util.arryToString = function(arrayData,split = '|'){
  return arrayData.join(split);
}
util.stringToArray = function(arrayStr,split = '|'){
  //        let form = JSON.stringify(row);
  //           self.strategy = JSON.parse(form);
  return arrayStr.split(split);
}
/**判断是否成功*/
util.isSuccess = function(res){
  return (res.success && '200' ==res.code );
}
/**返回数组*/
util.getData = function(res){
  return res.result;
}

/**返回数组*/
util.getPageData = function(res){
  if(res.result){
    return res.result.data.content;
  }
  return [];
}

util.getPageTotalCount = function(res){
  if(res.result){
    return res.result.data.totalElements;
  }
  return [];
}

util.isNull = function(str){
  return (!str || null == str || '' == str);
}

util.isNotNull = function(str){
  return !(util.isNull(str));
}

util.getDicNameByValue = function(dicData,value){
    return util.showNameFromList(dicData,value,"value","name");
}


// 列表中获取特定的值
util.showNameFromList = function (dataList,key,keyName='value',valueName='label') {
  let text = key;
  if(dataList){
    for (var i = 0; i < dataList.length; i++) {
      if(dataList[i][keyName] == key){
        text = dataList[i][valueName];
        break;
      }
    }
  }
  return text;
};



util.showProvinceFromList = function (dataList,key,keyName='provinceCode',valueName='provinceName') {
  let text = key;
  if(dataList){
    for (var i = 0; i < dataList.length; i++) {
      if(dataList[i][keyName] == key){
        text = dataList[i][valueName];
        break;
      }
    }
  }
  return text;
}

util.clone = function (obj) {
  let clonedObj = JSON.parse(JSON.stringify(obj));
  return clonedObj;
}



export default util;
