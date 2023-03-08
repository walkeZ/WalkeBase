package com.hui.huiheight.as;

/**
 * @author Walker - 2023/3/8 9:36 上午
 * @email 1126648815@qq.ocm
 * @desc : 创建类文件头格式调试
 * setting/Preference（Mac）-> Editor -> File and Code Templates
 * ① Files -> Class
 *      参考(调试后复原->上下的interface、enum等):
 *      #if (${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
 *      #parse("File Header.java")
 *      public class ${NAME} {
 *      }
 * ② Includes -> File Header
 *
 */

/**
 * ② Includes -> File Header
 * 参考：
 * @author Walker - ${DATE} ${TIME}
 * @email 1126648815@qq.ocm
 * @desc : ${desc}
 */

/**
 * @author walker
 * @date 2023/3/8
 * @desc 快捷导入文件头
 * setting/Preference（Mac）-> Editor -> Live Templates -> Android -> Add（右上角）
 */
public class TestCreate1 {
}