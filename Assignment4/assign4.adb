-- Ada Program Example

with Ada.Text_IO;
use Ada.Text_IO;


procedure assign4 is

	Author: String(1 .. 3);
	Title: String(1 .. 3);
	pages: String(1 .. 3);

function getAuthor return String is
		

begin
	return Author;

end getAuthor;


function getTitle return String is
		

begin
	return Title;

end getTitle;

function getPages return String is
		

begin
	return pages;

end getPages;


Procedure setAuthor(A : String) is
		

begin 

Author:= A;
	

end setAuthor;


Procedure setTitle(A : String) is
		

begin 

Title:= A;
	

end setTitle;

Procedure setPages(A : String) is
		

begin 

pages:= A;
	

end setPages;

begin 

Put_Line("Now we will test the set methods");
Put_Line("Please give me an author name, 3 letters exactly");
Get(Author);
Put_Line("Please give me a title 3 letters exacctly");
Get(Title);
Put_Line("Please give me the number of pages 3 digits exacly");
Get(pages);
Put_Line("Now testing the Getter Methods");
Put_Line("Author: " & Author);
Put_Line("Title: " & Title);
Put_Line("Pages: " & pages);

end assign4;
