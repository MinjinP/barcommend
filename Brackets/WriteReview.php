<?php 
    $con = mysqli_connect("localhost", "alswls97", "wls5484!", "alswls97");
    mysqli_query($con,'SET NAMES utf8');
 
    $userID = $_POST["userID"];
    $Gtin = $_POST["Gtin"];
    $ReviewText = $_POST["ReviewText"];
    $StarPoint = $_POST["StarPoint"];
    $ReviewDate = $_POST["ReviewDate"];
    
 
    $statement = mysqli_prepare($con, "INSERT INTO Review VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sisds", $userID, $Gtin, $ReviewText, $StarPoint, $ReviewDate);
    mysqli_stmt_execute($statement);
 
 
    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);
?>