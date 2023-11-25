package com.example.dotoring.ui.util.data

class SelectedDataSource {
    var selectedDataList: MutableList<String> = mutableListOf()

    fun loadSelections(): List<String> {
        return selectedDataList
    }

    fun addSelectedData(data: String) {
        selectedDataList.add(data)
    }

    fun deleteSelectedData(data: String) {
        selectedDataList.remove(data)
    }

    fun clearSelectedData() {
        selectedDataList.removeAll(selectedDataList)
    }
}