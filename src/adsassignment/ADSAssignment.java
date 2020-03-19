package adsassignment;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
class Bin{
    private ArrayList<Integer> values;
    int size,currSize;
    private boolean isFull;
    public Bin(int size) {
        this.size = size;
        isFull=false;
        currSize=0;
        values=new ArrayList<>();
    }
    public boolean isFull(){
        return isFull;
    }
    public void setFull(){
        isFull=true;
    }
    public void add(int value){
        values.add(value);
        currSize+=value;
    }
    void display(){
        String content="";
        for(int i=0;i<values.size();i++){
            content+=values.get(i)+" ";
        }
        System.out.println(content);
    }
    boolean search(int value){
        return values.contains(value);
    }
    
    
}
class BinPacking{
    ArrayList<Integer> objects;
    Bin bin[];
    int noOfBins;

    public BinPacking(ArrayList<Integer> objects, int sizeOfBin, int noOfBins) {
        this.objects = objects;
        this.noOfBins = noOfBins;
        bin=new Bin[noOfBins];
        for(int i=0;i<noOfBins;i++)
            bin[i]=new Bin(sizeOfBin);
    }
    void nextFit(){
        for(int i=0;i<objects.size();i++){
            for(int j=0;j<noOfBins;j++){
                if(!bin[j].isFull()){
                    if(bin[j].currSize+objects.get(i)>bin[j].size)
                       bin[j].setFull();
                    else{
                        bin[j].add(objects.get(i));
                        break;
                    }
                }
            }
        }
    }
    void firstFitBounded(){
        ArrayList<Integer> addedIndices=new ArrayList<>();//stores the indices of the elements already added
        for(int i=0;i<noOfBins;i++){
//            for(int j=0;j<objects.size();j++){
//                if(bin[i].isFull())
//                    break;
//                if((bin[i].currSize+objects.get(j))<=bin[i].size && !addedIndices.contains(j)){
//                    bin[i].add(objects.get(j));
//                    addedIndices.add(j);
//                }
//            }
            while(!bin[i].isFull()){
                int ndx=findMaxIndex(addedIndices,bin[i].size-bin[i].currSize);
                if(ndx!=-1){
                    bin[i].add(objects.get(ndx));
                    addedIndices.add(ndx);
                }
                else
                    break;
            }
        }
    }
    private int findMaxIndex(ArrayList<Integer> addedIndices,int sizeLeft){
        int max=-1,maxndx=-1;
        for(int i=0;i<objects.size();i++){
            if(addedIndices.contains(i))
                continue;
            if(max<objects.get(i) && objects.get(i)<=sizeLeft){
                max=objects.get(i);
                maxndx=i;
            }
        }
        return maxndx;
    }
    int calculateMemoryWasted(){
        int wasted=0;
        for(int i=0;i<noOfBins;i++){
            wasted+=bin[i].size-bin[i].currSize;
        }
        return wasted;
    }
    void display(){
        for(int i=0;i<noOfBins;i++){
            System.out.print("Bin"+(i+1)+" ");
            bin[i].display();
        }
    }
    
    void search(int value){
        String s=value+" exists in ";
        for(int i=0;i<noOfBins;i++){
            s+=(bin[i].search(value))?" Bin"+(i+1)+" ":"";
        }
        System.out.println(s);
    }
    
}
public class ADSAssignment {
    public static void main(String[] args) {
        //int object[]={6,3,2,1,5,4};
        int sizeOfBin=7;
        int noOfBins=50;
        ArrayList<Integer> objects=generateObjects(100,sizeOfBin);
        boolean exit=false;
        do{
            System.out.println("1. NextFit   2. First Fit Bounded   3. Exit");
            Scanner sc=new Scanner(System.in);
            
            switch(sc.nextInt()){
                case 1:
                    BinPacking b=new BinPacking(objects,sizeOfBin,noOfBins);
                    long start=System.nanoTime();
                    b.nextFit();
                    long end=System.nanoTime();
                    b.display();
                    System.out.println("Time for Next Fit="+(end-start)+"ns");
                    System.out.println("Memory wasted= "+b.calculateMemoryWasted());
                    System.out.println("Enter element to search:- ");
                    int ele=sc.nextInt();
                    start=System.nanoTime();
                    b.search(ele);
                    end=System.nanoTime();
                    System.out.println("Time for search in Next Fit="+(end-start)+"ns");
                    break;
                case 2:
                    BinPacking ffb=new BinPacking(objects,sizeOfBin,noOfBins);
                    start=System.nanoTime();
                    ffb.firstFitBounded();
                    end=System.nanoTime();
                    ffb.display();
                    System.out.println("Time for First Fit Bounded="+(end-start)+"ns");
                    System.out.println("Memory Wasted="+ffb.calculateMemoryWasted());
                    System.out.println("Enter element to search:- ");
                    ele=sc.nextInt();
                    start=System.nanoTime();
                    ffb.search(ele);
                    end=System.nanoTime();
                    System.out.println("Time for Search in First Fit Bounded="+(end-start)+"ns");
                    break;
                case 3:
                    exit=true;
            }
        }while(!exit);
         
//        BinPacking b=new BinPacking(objects,sizeOfBin,noOfBins);
//        b.nextFit();
//        b.display();
//        
//        System.out.println("Memory wasted= "+b.calculateMemoryWasted());
//        System.out.println("Enter element to search:- ");
//        
//        //Scanner sc=new Scanner(System.in);
//        int ele=sc.nextInt();
//        b.search(ele);
//          ArrayList<Integer> objectsffb=new ArrayList<>();
//          objectsffb.add(6);
//          objectsffb.add(5);
//          objectsffb.add(4);
//          objectsffb.add(1);
//          objectsffb.add(3);
//          objectsffb.add(2);
//          
//          BinPacking ffb=new BinPacking(objects,sizeOfBin,noOfBins);
//          ffb.firstFitBounded();
//          ffb.display();
//          System.out.println("Memory Wasted="+ffb.calculateMemoryWasted());
    }
    static ArrayList<Integer> generateObjects(int number,int sizeOfBin){
        ArrayList<Integer> arr=new ArrayList<>();
        Random r=new Random();
        for(int i=0;i<number;i++){
            int random=r.nextInt(sizeOfBin);
            if(random==0)
                i--;            
            else
               arr.add(random);
        }           
       // arr.add(6);arr.add(3);arr.add(2);arr.add(1);arr.add(5);arr.add(4);
        return arr;
    }
    
}
