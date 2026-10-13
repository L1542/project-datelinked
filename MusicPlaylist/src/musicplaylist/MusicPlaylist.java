package musicplaylist;
import java.util.Scanner;
public class MusicPlaylist {
        static class Node {
            String title;
            Node prev, next;
            Node(String title) {this.title = title;}
            public String toString() {return title;} }

        static class Playlist {
            Node head, tail;
            Node current;
            int size = 0;
            void add(String title) {
                if (title == null || title.isEmpty()) {
                    throw new IllegalArgumentException("ชื่อเพลงว่างเปล่า");}
                Node n = new Node(title);
                if (head == null) {head = tail = current = n;} 
                else {
                    tail.next = n;
                    n.prev = tail;
                    tail = n;}
                size++;}

            Node search(String title) {
                if (title == null) return null;
                for (Node cur = head; cur != null; cur = cur.next) {
                    if (cur.title.equalsIgnoreCase(title)) return cur;}
                return null;}

            int indexOf(Node target) {
                int i = 0;
                for (Node cur = head; cur != null; cur = cur.next, i++) {if (cur == target) return i;}
                return -1; }

            void delete(Node n) {
                if (n == null) {throw new IllegalArgumentException("ไม่พบเพลงที่ต้องการลบ");}
                if (current == n) {current = (n.next != null) ? n.next : n.prev;}
                if (n.prev != null) n.prev.next = n.next; else head = n.next;
                if (n.next != null) n.next.prev = n.prev; else tail = n.prev;
                n.prev = n.next = null;
                size--;}

            boolean isEmpty() {return size == 0;}
            void print() {
                int i = 1;
                for (Node cur = head; cur != null; cur = cur.next) { System.out.println(i++ + ". " + cur+ (cur == current ? "  <--- กำลังเล่น" : ""));}}}

        static class InputClosedException extends RuntimeException {}
        static final String LINE = "-----------";
        static final int MAX_LENGTH = 100;
        static Playlist playlist = new Playlist();
        static Scanner sc = new Scanner(System.in);

        static String readLine() {
            if (!sc.hasNextLine()) throw new InputClosedException();
            return sc.nextLine().trim();}

        static void showMenu() {
            System.out.println(LINE);
            System.out.println("เพลย์ลิสต์เพลง");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. Search");
            System.out.println("4. Next/Previous");
            System.out.println("0. ปิดโปรแกรม");
            System.out.println(LINE);}

        static void addSong() {
            System.out.print("กรอกชื่อเพลง : ");
            String title = readLine();
            if (title.isEmpty()) {System.out.println("ผิดพลาด: ชื่อเพลงว่างเปล่า");
                return;}
            if (title.length() > MAX_LENGTH) {
                System.out.println("ผิดพลาด: ชื่อเพลงยาวเกินไป (สูงสุด " + MAX_LENGTH + " ตัวอักษร)");
                return;}
            if (playlist.search(title) != null) {
                System.out.println("ผิดพลาด: มีเพลง \"" + title + "\" อยู่ในเพลย์ลิสต์แล้ว");
                return;}
            playlist.add(title);
            System.out.println("เพิ่มเพลง \"" + title + "\" สำเร็จ");}

        static void deleteSong() {
            if (playlist.isEmpty()) {
                System.out.println("ผิดพลาด: เพลย์ลิสต์ว่างเปล่า");
                return;}
            System.out.print("กรอกชื่อเพลงที่ต้องการลบ : ");
            String title = readLine();
            if (title.isEmpty()) {
                System.out.println("ผิดพลาด: ชื่อเพลงว่างเปล่า");
                return;}
            Node found = playlist.search(title);
            if (found == null) {
                System.out.println("ผิดพลาด: ไม่พบเพลง \"" + title + "\"");
                return;}
            String realTitle = found.title;
            playlist.delete(found);
            System.out.println("ลบเพลง \"" + realTitle + "\" สำเร็จ");}

        static void searchSong() {
            if (playlist.isEmpty()) {
                System.out.println("ผิดพลาด: เพลย์ลิสต์ว่างเปล่า");
                return;}
            while (true) {
                System.out.print("ค้นหาเพลง (กด Enter เพื่อย้อนกลับ) : ");
                String title = readLine();
                if (title.isEmpty()) return;
                Node found = playlist.search(title);
                if (found != null) {
                    System.out.println("พบเพลง!");
                    System.out.println("ชื่อเพลง : " + found.title);
                    System.out.println("ลำดับที่  : " + (playlist.indexOf(found) + 1));
                    return;}
                System.out.println("ผิดพลาด: ไม่พบเพลง กรุณากรอกใหม่อีกครั้ง");}}

        static void nextPrevious() {
            if (playlist.isEmpty()) {
                System.out.println("ผิดพลาด: เพลย์ลิสต์ว่างเปล่า");
                return;}
            while (true) {
                System.out.println("---- เพลย์ลิสต์ ----");
                playlist.print();
                System.out.println("--------------------");
                System.out.println("กำลังเล่น : " + playlist.current);
                System.out.print("[N] Next  [P] Previous  [B] Back : ");
                String cmd = readLine().toLowerCase();
                if (cmd.equals("n")) {
                    if (playlist.current.next != null) playlist.current = playlist.current.next;
                    else System.out.println("นี่คือเพลงสุดท้ายแล้ว"); } 
                else if (cmd.equals("p")) {
                    if (playlist.current.prev != null) playlist.current = playlist.current.prev;
                    else System.out.println("นี่คือเพลงแรกแล้ว");} 
                else if (cmd.equals("b")) {
                    return;} 
                else {
                    System.out.println("ผิดพลาด: คำสั่งไม่ถูกต้อง ให้ใช้ N, P หรือ B");}}}

        public static void main(String[] args) {
            try {
                while (true) {
                    try {
                        showMenu();
                        System.out.print("เลือกเมนู : ");
                        String input = readLine();
                        if (input.isEmpty()) {
                            System.out.println("ผิดพลาด: กรุณากรอกตัวเลข");
                            continue;}
                        int choice;
                        try {
                            choice = Integer.parseInt(input);
                        } catch (NumberFormatException e) {
                            System.out.println("ผิดพลาด: \"" + input + "\" ไม่ใช่ตัวเลขที่ถูกต้อง");
                            continue;}
                        switch (choice) {
                            case 1: addSong();      break;
                            case 2: deleteSong();   break;
                            case 3: searchSong();   break;
                            case 4: nextPrevious(); break;
                            case 0:
                                System.out.println("ลาก่อน!");
                                return;
                            default:
                                System.out.println("ผิดพลาด: เลขไม่ถูกต้อง เลือกได้ 0-4");}
                    } catch (InputClosedException e) {
                        throw e;
                    } catch (RuntimeException e) {
                        System.out.println("เกิดข้อผิดพลาดที่ไม่คาดคิด: " + e.getMessage());}}
            } catch (InputClosedException e) {
                System.out.println("\nปิดการรับข้อมูลแล้ว ลาก่อน!");
            } finally {sc.close();}}}
