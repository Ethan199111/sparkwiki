package auc;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * 使用BlockingQueue多线程技术，在一个目录及它的所以子目录下搜索所有文件，打印出包含关键字的文件及行号
 *
 */
public class BlockingQueueTest {
  
  private static final int FILE_QUEUE_SIZE = 10;
  private static final int SEARCH_THREADS = 100; // 搜索线程数
  private static final File DUMMY = new File(""); // 虚拟空文件夹，作为线程结束标志
  // 阻塞队列，先进先出
  private static BlockingQueue<File> queue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);
  
  public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)) {
      System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src): ");
      String directory = in.nextLine();
      System.out.print("Enter keyword (e.g. BlockingQueueTest): ");
      String keyword = in.nextLine();
      
      // 开启一个线程，递归枚举给定目录及其所有子目录下所有文件的文件名
      Runnable enumerator = () -> {
        try {
          enumerate(new File(directory));
          queue.put(DUMMY);  // 最后添加进空文件，以空文件作为结束标志
        } catch (InterruptedException e) {
        }
      };
      new Thread(enumerator).start();
      
      // 开启 SEARCH_THREADS(100) 个线程，查找所有文件中具有keyword的行
      for (int i = 1; i <= SEARCH_THREADS; i++) {
        Runnable searcher = () -> {
          try {
            boolean done = false;
            while (!done) {
              File file = queue.take();  // 取出队列中的文件名
              if (file == DUMMY) {
                queue.put(file);   // 空文件则说明已经结束
                done = true;
              } else
                search(file, keyword);
            }
          } catch (IOException e) {
            e.printStackTrace();
          } catch (InterruptedException e) {
          }
        };
        new Thread(searcher).start();
      }
    }
  }
  
  /**
   * 递归枚举给定目录及其所有子目录下所有文件的文件名
   *
   * @param directory
   *            开始搜索的目录
   * @throws InterruptedException InterruptedException
   */
  public static void enumerate(File directory) throws InterruptedException {
    File[] files = directory.listFiles(); // 返回目录下所有文件名和目录的list迭代器
    for (File file : files) {
      if (file.isDirectory()) // 判断是否的目录
        enumerate(file); // 对于目录继续递归枚举
      else
        queue.put(file); // 文件则将文件名加入队列
    }
  }
  
  /**
   * 打开文件，搜索文件中包含keyword的行
   *
   * @param file
   *            搜索关键字的文件
   * @param keyword
   *            用于搜索的关键字
   * @throws IOException IOException
   */
  public static void search(File file, String keyword) throws IOException {
    try (Scanner in = new Scanner(file, "UTF-8")) {
      int lineNumber = 0;
      while (in.hasNextLine()) {
        lineNumber ++;
        String line = in.nextLine();
        if (line.contains(keyword))    // 打印文件中包含 keyword 的行的信息
          System.out.printf("%s:%d:%s%n", file.getPath(), lineNumber, line);
      }
    }
  }
}