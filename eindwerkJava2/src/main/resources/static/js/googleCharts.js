
        var real_data = /*[[${chartData}]]*/'noValue';
        $(document).ready(function() {
            google.charts.load('current', {
                packages : [ 'corechart', 'bar' ]
            });
            google.charts.setOnLoadCallback(drawColumnChart);
            google.charts.setOnLoadCallback(drawPieChart);
        });
        function drawColumnChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Year');
            data.addColumn('number', 'Views');
            Object.keys(real_data).forEach(function(key) {
                data.addRow([ key, real_data[key] ]);
            });
            var options = {
                title : 'Blog stats',
                hAxis : {
                    title : 'Years',
                },
                vAxis : {
                    title : 'View Count'
                }
            };
            var chart = new google.visualization.ColumnChart(document
                    .getElementById('chart_div'));
            chart.draw(data, options);
        }
        function drawPieChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Year');
            data.addColumn('number', 'Views');
            Object.keys(real_data).forEach(function(key) {
                data.addRow([ key, real_data[key] ]);
            });
            var options = {
                title : 'Blog stats'
            };
            var chart = new google.visualization.PieChart(document
                    .getElementById('piechart'));
            chart.draw(data, options);
        }
