<template>
        <div class="app-container documentation-container">
                <a class="document-btn" target="_blank" >主页名</a>
                <label v-model="title" class="titlestyle">{{title}}</label>
        </div>
</template>

<script>
    import {titleList, titleUpdate} from '@/api/company'
    export default {
        data() {
            return {
                title: {
                    title:"titl"
                },
                dialogFormVisible:false
            }

        },
        created(){
            this.getData();
        },
        methods: {
            getData() {
                this.getTitle();
            },
            getTitle() {
                titleList().then(response=>{
                    console.log(response,'sdll')
                     if(response.data.infoCod){
                        this.$message({
                            message: response.data.description,
                            type: 'warning'
                        });
                    }else{
                       this.title = response.data;
                    }
                })

            }

        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .documentation-container {
      margin: 50px;
      .document-btn {
        float: left;
        margin-left: 50px;
        display: block;
        cursor: pointer;
        background: black;
        color: white;
        height:60px;
        width: 100px;
        line-height: 60px;
        font-size: 20px;
        text-align: center;
      }
    };
    .titlestyle {
        margin-left: 100px;
        line-height: 60px;
        font-size: 20px;
    }
    </style>
