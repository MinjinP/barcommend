<?php
    $con = mysqli_connect("localhost", "alswls97", "wls5484!", "alswls97");
    mysqli_query($con,'SET NAMES utf8');
 
    $Gtin = $_POST["Gtin"];
    
    $statement = mysqli_prepare($con, "SELECT Review.ReviewText, Review.StarPoint, Review.ReviewDate, register.userName FROM Review, register WHERE register.userID=Review.userID AND Review.Gtin=?");

    mysqli_stmt_bind_param($statement, "s", $Gtin);
    mysqli_stmt_execute($statement);
 
 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $ReviewText, $StarPoint, $ReviewDate, $userName);
 
    $response = array();
    $allresponse = array();
    $response["success"] = true;
 
    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["ReviewText"] = $ReviewText;
        $response["StarPoint"] = $StarPoint;
        $response["ReviewDate"] = $ReviewDate;
        $response["userName"] = $userName;
        $allresponse[] = $response;
    }
 
    echo json_encode($allresponse);
?>