
//GET DATA FOR ADOPTED PETS:

//get pet types:

    //get array of pet types
    d3.selectAll('#type')
    var typesList = d3.selectAll('#type')['_groups'][0]
    var types = []
    for (i = 0; i < typesList.length; i++) {
        types.push(typesList.item(i).textContent);
    }
    //get array of pet types

    //get number of dogs and cats
    var catsNum = 0;
    var dogsNum = 0;
    for (i = 0; i < types.length; i++) {
        if(types[i].toString().localeCompare('CAT') == 0){
            catsNum++;
        }
        else if(types[i].toString().localeCompare('DOG') == 0){
            dogsNum++;
        }
    }

    var typesDataset = [];
    typesDataset.push(dogsNum);
    typesDataset.push(catsNum);
    console.log(typesDataset)
    //get number of dogs and cats


    var svgWidth = 400, svgHeight = 400, barPadding = 20;
    var barWidth = (svgWidth / typesDataset.length);


    var svg = d3.select('svg')
      .attr("width", svgWidth)
        .attr("height", svgHeight);

    /*var xScale = d3.scaleLinear()
        .domain([0, d3.max(typesDataset)])
        .range([0, svgWidth]);*/

  /*  var yScale = d3.scaleLinear()
        .domain([-1.2, d3.max(typesDataset)])
        .range([ svgHeight, 0]);*/

    var yScaleNew = d3.scaleLinear()
        .domain([0, d3.max(typesDataset)])
        .range([ 0, svgHeight*0.9]);

    /*  var x_axis = d3.axisBottom()
          .scale(xScale);*/

   /* var y_axis = d3.axisLeft()
        .scale(yScale)

    svg.append("g")
        .attr("transform", "translate(20, 10)")
        .call(y_axis);

    var xAxisTranslate = svgHeight - 30;*/

    // svg.append("g")
    //   .attr("transform", "translate(50, " + xAxisTranslate +")")
    // .call(x_axis);




   /* var text = svg.selectAll("text")
        .data(typesDataset)
        .enter()
        .append("text")
        .text(function(d) {
            return d;
        })
        .attr("y", function(d, i) {
            return svgHeight - d - 2;
        })
        .attr("x", function(d, i) {
            return barWidth * i;
        })
        .attr("fill", "#A64C38");*/

var barChart = svg.selectAll("rect")
    .data(typesDataset)
    .enter()
    .append("rect")
    .attr("id", "typeRect")
    .attr("y", function(d){
        return svgHeight - yScaleNew(d)
    })
    .attr("height", function(d){
        return yScaleNew (d);
    })
    .attr("width", barWidth - barPadding)
    .attr("transform", function (d, i){
        var translate = [barWidth * i + 20, -30];
        return "translate(" + translate +")";
    });

var first = false;
var text = svg.selectAll("text")
    .data(typesDataset)
    .enter()
    .append("text")
    .text(function(d) {
        if(first == false){
            first = true;
            return "DOGS";
        }
        else {
            return "CATS";
        }
    })
    .attr("y", function(d, i) {
        return svgHeight - d + 4;
    })
    .attr("x", function(d, i) {
        return barWidth * i + 70;
    })
    .attr("fill", "#A64C38")
    .attr("z-index", 100);



    var yScale = d3.scaleLinear()
          .domain([-1.2, d3.max(typesDataset)])
          .range([ svgHeight, 0]);
      var y_axis = d3.axisLeft()
          .scale(yScale)

      svg.append("g")
          .attr("transform", "translate(20, 10)")
          .call(y_axis);

      var xAxisTranslate = svgHeight - 30;



//ADOPTION DATES

//get adoption dates:
d3.selectAll('#date')
var datesList = d3.selectAll('#date')['_groups'][0]
var dates = []
for (i = 0; i < datesList.length; i++) {
    dates.push(datesList.item(i).textContent);
}





