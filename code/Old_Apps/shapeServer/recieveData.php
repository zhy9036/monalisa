<html>
<head>
    <title>
        Recieving Data
    </title>
</head>
<body>
<p align='center'>
    This page is receiving data.
</p>
<p align="center">
<?php
  $n="serverFile.xml";
  $f=fopen($n,'w');
  $value=$_GET['value'];
  echo "Page Recieved: " . $value;
  fwrite($f,$value);
  fclose($f);
  ?>
	</p>
</body>
</html>