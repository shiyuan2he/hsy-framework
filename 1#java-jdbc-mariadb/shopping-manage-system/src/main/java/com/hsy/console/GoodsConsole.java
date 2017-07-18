package com.hsy.console;

import com.hsy.dao.IGoodsDao;
import com.hsy.dao.impl.GoodsDaoImpl;
import com.hsy.entity.Goods;

import java.util.List;

import static com.hsy.util.ScannerChoice.*;

/**
 * @author heshiyuan
 * @description <p></p>
 * @date 2017/7/18 11:19
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
public class GoodsConsole {
    /**
     *
     * @description <p>添加商品控制台</p>
     * @returnType Map<String,String>
     * 方法名:
     * 类名:GoodsConsole
     * @author heshiyuan
     * @email shiyuan4work@sina.com
     * @date 2017/7/18 11:24
     * @price ￥:5毛
     * @copyright	此方法版权归本人所有，复制或者剪切请通知本人或者捐赠 通知方式：shiyuan4work@sina.com
     * @callnumber 15910868535
     */
    public void addGoodsConsole() {
        IGoodsDao goodsDao = new GoodsDaoImpl();
        System.out.println("\t正在执行添加商品操作\n");
        System.out.println("\n请输入添加商品-名称");
        String goodsName = ScannerInfoString();
        System.out.println("\n请输入添加商品-价格");
        double goodsPrice = ScannerInfo();
        System.out.println("\n请输入添加商品-数量");
        int goodsNumber = ScannerNum();
        Goods goods  = new Goods(System.currentTimeMillis(),goodsName,goodsPrice,goodsNumber);
        boolean bool = goodsDao.addGoods(goods);

        if (bool){
            System.out.println("\n\t!您已成功添加商品到数据库!");
        }else{
            System.out.println("添加商品失败");
        }
        changedInfoNext("addGoodsPage");//选择下一步
    }

    /**
     * 2.更改商品界面
     */
    public void  upateGoodsConsole(){
        IGoodsDao goodsDao = new GoodsDaoImpl();
        System.out.println("\t正在执行 更改商品 操作\n");
        System.out.println("请输入想要更改的商品名字");

        //调用查找商品函数，显示将要更改的商品信息
        Long gid = goodsDao.query("upateGoodsPage"); //return the goods gid
        System.out.println("\n--------请选择您要更改的内容\n");
        System.out.println("\t1.更改商品-名称");
        System.out.println("\t2.更改商品-价格");
        System.out.println("\t3.更改商品-数量");
        System.out.println("\n请输入选项,或者按0返回上一级菜单.");
        do{
            String choice = ScannerInfoString();
            String regex = "[0-3]";
            if (choice.matches(regex)){
                int info = Integer.parseInt(choice);
                switch (info){
                    case 0:
                        MainConsole.CommodityMaintenanceConsole();
                        break;
                    case 1:
                        System.out.println("请输入商品-新名称");
                        String gname = ScannerInfoString();
                        Goods goodsName = new Goods(gid,gname);
                        boolean boolName = goodsDao.updateGoods(1, goodsName);
                        if (boolName){
                            System.out.println("\n\t！！成功更新商品名至数据库！！\n");
                        }else{
                            System.err.println("\n\t！！更新商品名失敗！！");
                        }
                        changedInfoNext("upateGoodsPage");
                        break;
                    case 2:
                        System.out.println("请输入商品-新价格 ");
                        double gprice = ScannerInfo();
                        Goods  goodsPrice = new Goods(gid,gprice);
                        boolean boolPrice = goodsDao.updateGoods(2,goodsPrice);
                        if (boolPrice){
                            System.out.println("\n\t！！成功更新商品价格至数据库！！\n");
                        }else{
                            System.err.println("\n\t！！更新商品价格失敗！！");
                        }
                        changedInfoNext("upateGoodsPage");
                        break;
                    case 3:
                        System.out.println("请输入商品-新数量 ");
                        int gNum = ScannerNum();
                        Goods  goodsNum= new Goods(gid,gNum);
                        boolean boolNum = goodsDao.updateGoods(3,goodsNum);
                        if (boolNum){
                            System.out.println("\n\t！！成功更新商品数量至数据库！！\n");
                        }else{
                            System.err.println("\n\t！！更新商品数量失敗！！");
                        }
                        changedInfoNext("upateGoodsPage");
                        break;
                    default:
                        System.out.println("请输入正确的选择！");
                        break;
                }
            }
            System.err.println("！输入有误！");
            System.out.println("请重新选择,或者按0返回上一级菜单.");
        } while (true);
    }

    /**
     * 3.删除商品界面
     */
    public void deleteGoodsConsole(){
        IGoodsDao goodsDao = new GoodsDaoImpl();
        System.out.println("\t正在执行 删除商品 操作\n");
        System.out.println("请输入想要删除的商品名字");
        //调用查找商品函数，显示将要删除的商品
        Long gid = goodsDao.query("deleteGoodsPage"); //return the goods gid
        //确认删除！
        do{
            System.out.println("\n确认删除该商品：Y/N");
            String choice = ScannerInfoString();
            if ("y".equals(choice) || "Y".equals(choice)){
                //进行刪除-数据库操作
                boolean boolDeleteGoods = goodsDao.deleteGoods(gid);//調用刪除功能
                if (boolDeleteGoods){
                    System.err.println("\t！！已成功刪除该商品！！\n");
                }else{
                    System.err.println("\n\t！！刪除该商品失敗！！");
                }
                changedInfoNext("deleteGoodsPage");
            }else if ("N".equals(choice) || "n".equals(choice)){
                MainConsole.CommodityMaintenanceConsole();
            }
            System.out.println("\t!!输入有误,请重新输入!!\n");
        } while (true);
    }

    /**
     * 4.查询商品界面
     */
    public static void queryGoodsConsole(){
        IGoodsDao goodsDao = new GoodsDaoImpl();
        System.out.println("\t\t  正在执行查询商品操作\n");
        System.out.println("\t\t1.按照商品 数量升序 查询");
        System.out.println("\t\t2.按照商品 价格升序 查询");
        System.out.println("\t\t3.输入商品  关键字  查询");
        System.out.println("\n请输入选项,或者按0返回上一级菜单.");
        do{
            String info = ScannerInfoString();//用户选择上述提示信息
            String regex = "[0-3]";
            if (info.matches(regex)){
                int choice = Integer.parseInt(info);
                switch (choice){
                    case 0:
                        MainConsole.CommodityMaintenanceConsole();
                        break;
                    case 1:
                    case 2:
                    case 3:
                        //当用户使用3（即关键字查询）时，需要打印此项目。
                        if (choice == 3){
                            System.out.println("\t\t正在执行商品  关键字  查询操作\n");
                            System.out.println("\n请输入商品关键字");
                        }
                        //调用查询功能
                        List<Goods> goodsList = goodsDao.queryGoods(choice);
                        if (goodsList == null || goodsList.size() <= 0){
                            System.err.println("\n\t!!您查询的商品不存在!!\n");
                            queryGoodsConsole();
                        }else{
                            //打印目录，不要放在功能函数中，会影响其他方法调用
                            if (choice == 1){
                                System.out.println("\t\t\t\t\t商品按照 数量升序 列表\n\n");
                            }else if (choice == 2){
                                System.out.println("\t\t\t\t\t商品按照 价格升序 列表\n\n");
                            }else{
                                System.out.println("\t\t\t\t\t您所查找的商品如下\n\n");
                            }
                            System.out.println("\t商品编号\t\t商品名称\t\t商品价格\t\t商品数量\t\t备注\n");
                            //遍历数组（存放用户查找的信息）
                            for (int i = 0,length = goodsList.size(); i < length; i++){
                                Goods goods = goodsList.get(i);
                                System.out.print("\t"+goods.getId()+"\t\t"+goods.getName()+"\t\t"+goods.getPrice()+"\t\t"+goods.getNumber());
                                int gnum = goods.getNumber();
                                if (gnum ==0){
                                    System.out.println("\t\t该商品已售空！");
                                }else if (gnum<10){
                                    System.out.println("\t\t该商品已不足10件");
                                }else{
                                    System.out.println("\t\t-");
                                }
                                System.out.println("\t");
                            }
                            System.out.println("---------------------");
                            do{
                                System.out.println("输入 0 返回上一级菜单");
                                String choiceNext = ScannerInfoString();

                                if ("0".equals(choiceNext)){
                                    MainConsole.CommodityMaintenanceConsole();
                                }
                                System.err.println("输入有误！");
                            } while (true);
                        }
                        break;
                    default:
                        break;
                }
                break;
            }
            System.err.println("输入有误！");
            System.out.println("请重新选择,或者按0返回上一级菜单.");
        } while (true);

        //用户选择操作完查询后的下一步
        System.out.println("\n\n输入 0 返回上一级菜单");
        boolean boolNext = true;
        do{
            String choice = ScannerInfoString();
            if ("0".equals(choice)){
                boolNext = false;
                queryGoodsConsole();
            }
            System.err.println("!输入有误!");
            System.out.println("请输入 0 返回上一级菜单");
        } while (boolNext);
    }

    /**
     * 5.展示所有商品界面
     */
    public static void displayGoodsConsole(){
        IGoodsDao goodsDao = new GoodsDaoImpl();
        System.out.println("\t\t\t\t\t所有商品列表\n\n");
        List<Goods> goodsList = goodsDao.displayGoods();
        if (goodsList.size() <= 0){
            System.err.println("！库存为空！");
            MainConsole.CommodityMaintenanceConsole();
        }else{
            System.out.println("\t商品编号\t\t商品名称\t\t商品价格\t\t商品数量\t\t备注\n");
            //避免重复计算变量，浪费资源！
            for (int i = 0,length = goodsList.size(); i < length; i++){
                Goods goods = goodsList.get(i);
                System.out.print("\t"+goods.getId()+"\t\t"+goods.getName()+"\t\t"+goods.getPrice()+" $\t\t"+goods.getNumber());

                int gNum = goods.getNumber();
                if (gNum==0){
                    System.out.println("\t\t该商品已售空！");
                }else if (gNum<10){
                    System.out.println("\t\t该商品已不足10件");
                }else{
                    System.out.println("\t\t-");
                }
                System.out.println("\t");
            }
            //下一步
            System.out.println("---------------------");
            do{
                System.out.println("输入 0 返回上一级菜单");
                String choice = ScannerInfoString();
                if ("0".equals(choice)){
                    MainConsole.CommodityMaintenanceConsole();
                }
                System.out.println("输入有误！");
            } while (true);
        }
    }
}
