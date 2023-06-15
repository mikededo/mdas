lint:
	latexindent *.tex -s -w

compile:
	xelatex pf.tex

build:
	make lint && make compile

clean:
	@rm -f `find . -type f -name '*aux' -o -name '*bak*' -o -name '*lof' -o -name '*log' -o -name '*toc' -o -name '*.DS_Store'`
