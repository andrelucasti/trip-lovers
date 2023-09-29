<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>TripLovers</title>
    <body>
        <h1>Test</h1>

        <p>Test</p>

        <ul>
            <#list trips as trip>
                <li>${trip.title}</li>
            </#list>
        </ul>
    </body>
</html>