<?php 
    $con = mysqli_connect("localhost", "alswls97", "wls5484!", "alswls97");
    mysqli_query($con,'SET NAMES utf8');
 
    $userID = $_POST["userID"];
    $userPassword = $_POST["userPassword"];
    $userName = $_POST["userName"];
    $userPhone = $_POST["userPhone"];
 
    $statement = mysqli_prepare($con, "INSERT INTO register VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssi", $userID, $userPassword, $userName, $userPhone);
    mysqli_stmt_execute($statement);
 
 
    $response = array();
    $response["success"] = true;
 
   
    echo json_encode($response);
?>