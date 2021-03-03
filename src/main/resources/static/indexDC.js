
//GET DATA FOR ADOPTED PETS:

//ADOPTED:
var cityChart = dc.rowChart("#aCityVisu")
//var cityUserChart = dc.rowChart("#aCityUserVisu")
var ageChart = dc.rowChart("#aAgeVisu")
var breedChart = dc.rowChart("#aBreedVisu")
var genderChart =  dc.pieChart('#aGenderVisu');
var typeChart =  dc.pieChart('#aTypeVisu');
var centerChart = dc.rowChart("#aCenterVisu");
var adoptedChart =  dc.pieChart('#adoptedVisu');
var visCount = dc.dataCount(".dc-data-count")
var visTable = dc.dataTable(".dc-data-table")

    var citiesList = d3.selectAll('#petCity')[0]
    var centerList = d3.selectAll('#center')[0]
    var breedList = d3.selectAll('#breed')[0]
    var genderList = d3.selectAll('#gender')[0]
    var ageList = d3.selectAll('#age')[0]
    var typeList = d3.selectAll('#type')[0]
    var arrivalList = d3.selectAll('#arrivalDate')[0]
    var dateList = d3.selectAll('#date')[0]
    var ownerCityList = d3.selectAll('#ownerCity')[0]

    var citiesListNA = d3.selectAll('#petCityNA')[0]
    var centerListNA = d3.selectAll('#centerNA')[0]
    var breedListNA = d3.selectAll('#breedNA')[0]
    var genderListNA = d3.selectAll('#genderNA')[0]
    var ageListNA = d3.selectAll('#ageNA')[0]
    var typeListNA = d3.selectAll('#typeNA')[0]
    var arrivalListNA = d3.selectAll('#arrivalDateNA')[0]
    var dateListNA = d3.selectAll('#dateNA')[0]
    var ownerCityListNA = d3.selectAll('#ownerCityNA')[0]

    var objects = [];
    for (i = 0; i < citiesList.length; i++) {
        var obj = {
            city: citiesList[i].textContent,
            center: centerList[i].textContent,
            breed: breedList[i].textContent,
            gender: genderList[i].textContent,
            age: ageList[i].textContent,
            type: typeList[i].textContent,
            arrivalDate: arrivalList[i].textContent,
            date: dateList[i].textContent,
            ownerCity: ownerCityList[i].textContent,
            adopted: true
        }
        objects.push(obj);
    }

    for (i = 0; i < citiesListNA.length; i++) {
        var obj = {
            city: citiesListNA[i].textContent,
            center: centerListNA[i].textContent,
            breed: breedListNA[i].textContent,
            gender: genderListNA[i].textContent,
            age: ageListNA[i].textContent,
            type: typeListNA[i].textContent,
            arrivalDate: arrivalListNA[i].textContent,
            date: dateListNA[i].textContent,
            adopted: false
        }
        objects.push(obj);
    }
    console.log(objects);



    var ndx= crossfilter(objects);
    var all = ndx.groupAll();

    var cityDim = ndx.dimension(function (d){
        //console.log(d["city"])
        return d["city"];
    })
    var genderDim = ndx.dimension(function (d){
        //console.log(d["city"])
        return d["gender"];
    })
    var cityOwnerDim = ndx.dimension(function (d){
        return d["ownerCity"];
    })
    var typeDim = ndx.dimension(function (d){
        return d["type"];
    })
    var centerDim = ndx.dimension(function (d){
        return d["center"];
    })
    var ageDim = ndx.dimension(function (d){
        return d["age"];
    })
    var breedDim = ndx.dimension(function (d){
        return d["breed"];
    })
    var adoptedDim = ndx.dimension(function (d){
        return d["adopted"];
    })
    var dateDim = ndx.dimension(function (d){
        return d["arrivalDate"];
    })

    var cityGroup = cityDim.group();
    var cityOwnerGroup = cityOwnerDim.group();
    var genderGroup = genderDim.group();
    var typeGroup = typeDim.group();
    var centerGroup = centerDim.group();
    var ageGroup = ageDim.group();
    var breedGroup = breedDim.group();
    var adoptedGroup = adoptedDim.group();
    var dateGroup = adoptedDim.group();

    cityChart
        .dimension(cityDim)
        .group(cityGroup)
        .height(340)
        .width(400);

    genderChart
        .dimension(genderDim)
        .group(genderGroup)
        .height(340)
        .width(300);

typeChart
    .dimension(typeDim)
    .group(typeGroup)
    .height(340)
    .width(300);

/*
cityUserChart
    .dimension(cityOwnerDim)
    .group(cityOwnerGroup)
    .height(340)
    .width(400);
*/

centerChart
    .dimension(centerDim)
    .group(centerGroup)
    .height(340)
    .width(400);

ageChart
    .dimension(ageDim)
    .group(ageGroup)
    .height(340)
    .width(300);

breedChart
    .dimension(breedDim)
    .group(breedGroup)
    .height(340)
    .width(300);

adoptedChart
    .dimension(adoptedDim)
    .group(adoptedGroup)
    .height(340)
    .width(300);

visCount
    .dimension(ndx)
    .group(all);

visTable
    .dimension(dateDim)
   // .group(dateGroup)
    .group(function(d){
        var format = d3.format('02d');
        return d.dd.getFullYear() + '/' + format((d.dd.getMonth() + 1))
    })
    .columns([
        "Timestamp",
        "center",
        "adopted"
    ]);

    dc.renderAll();












