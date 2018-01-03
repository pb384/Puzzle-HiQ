#include <iostream>
#include <fstream>
#include <cstdio>
#include <cstdlib>

using namespace std;

int mypegs[14];
static int bettersol;
static int lines_req;
static int moves[15][3];

int moves_allowed[100][3];// = {{0, 1, 3},{0,2,5},{1, 3, 6},{1, 4, 8},{2, 4, 7},{2, 5, 9},{3, 4, 5},{3, 6, 10},{3, 7, 12},{4, 7, 11},{4, 8, 13},\
{5, 8, 12},{5, 9, 14},{6, 7, 8},{7, 8, 9},{10, 11, 12},{11, 12, 13},{12, 13, 14}};

void uploadmymoves(){
    
    ifstream myfile("mymoves.txt");
    string line;
    if(myfile.is_open()){
        while(!myfile.eof()){
            getline(myfile,line);
            lines_req++;
        }
        myfile.close();
    }
    FILE *f;
    f = fopen("mymoves.txt","r");
    fseek( f, 0, SEEK_SET );
    for(int i=0;i<18;i++) for(int j=0;j<3;j++) fscanf(f,"%d",&moves_allowed[i][j]);
    fclose(f);
}
int findmyrow(int num){
    int i = 0;
    int count = 2;
    if(num==0) return 1;
    while(i<=num) {
        i+= count;
        count++;
    }
    return count-1;
}

int check_possiblemove(int start, int jump, int end){
    
    for(int i=0;i<lines_req-1;i++){
        if(mypegs[start]==1 && mypegs[end]==0 && mypegs[jump]==1){
            if(start == moves_allowed[i][0] && jump == moves_allowed[i][1] && end==moves_allowed[i][2]) return 1;
            if(start == moves_allowed[i][2] && jump == moves_allowed[i][1] && end==moves_allowed[i][0]) return 1;
        }
    }
    return 0;
}

void checkforsolution(int num_moves){
    
    int i,j,k;
    int count=0;
    char in;
    
    for(i=0;i<15;i++) if(mypegs[i]==1){
        count+=1;
    }
    // cout << "Moves: "<< num_moves << " Count: "<< count<<endl;// " better: "<<bettersol<<endl;
    if(count<=bettersol || count==1){
        cout << "No. of Moves: "<< num_moves << " \nPegs Remaining: "<< count<<endl;
        for(j=0;j<num_moves;j++) cout<< moves[j][0] << " " << moves[j][1] << " " << moves[j][2] << endl;
        cout << endl;
        cout << "Do you want to search for a better solution? (y/n): ";
        cin >> in;
        if(in=='n'){
            cout << "Thank you!! Solution is in output.txt .\n" ;
            FILE *f;
            f= fopen("output.txt","w");
            for(i=0;i<num_moves;i++) {
                for(j=0;j<3;j++) fprintf(f,"%d ",moves[i][j]);
                fprintf(f, "\n");
            }
            exit(1);
        }
        if(bettersol!=1) bettersol--;
    }
    for(k=0;k<15;k++){
        if(mypegs[k]==1) continue;
        for(i=0;i<15;i++){
            for(j=0;j<15;j++){
                if(check_possiblemove(i,j,k)){
                    
                    mypegs[i] = 0;
                    mypegs[j] = 0;
                    mypegs[k] = 1;
                    
                    moves[num_moves][0] = i;
                    moves[num_moves][1] = j;
                    moves[num_moves][2] = k;
                    
                    checkforsolution(num_moves+1);
                    
                    mypegs[i] = 1;
                    mypegs[j] = 1;
                    mypegs[k] = 0;
                }
            }
        }
    }
}
int main(){
    
    int init_hole;
    int i;
    int better;
    int moves[15][3];
    
    uploadmymoves();
    
    printf("Initial hole to be empty at: ");
    scanf("%d",&init_hole);
    printf("No of pegs you want to leave at the end of game: ");
    scanf("%d",&better);
    
    bettersol = better;
    for(i=0;i<15;i++) mypegs[i] = 1;
    mypegs[init_hole] = 0;
    checkforsolution(0);
}
