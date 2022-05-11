package com.example.gitrepo



data class ViewState<T> (val status : Status, val data :T?=null, val errorResponse: String?=null) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object{
        fun<T> success(data: T): ViewState<T>{
            return ViewState(Status.SUCCESS,data)
        }

        fun<T> error(errorResponse: String?): ViewState<T>{
            return ViewState(Status.ERROR,null,errorResponse)
        }

        fun<T> loading(): ViewState<T>{
            return ViewState(Status.LOADING)
        }
    }
}