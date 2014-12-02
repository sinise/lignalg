import java.lang.RuntimeException;

/**
 * This is a 2 dimensional real matrix class.  Objects of this
 * type are instantiated as e.g.
 *
 * Matrix A = Matrix(3, 2);
 *
 * in which case, A is a matrix with 3 rows and 2 columns of
 * doubles. The matrix elements are numerated from 1, i.e. the top
 * left element is (1, 1) and the bottom right is (3, 2) in the
 * example.
 *
 * @author Jon Sporring  {@literal<sporring@diku.dk>}
 * @version 1.1
 * @since 2013-10-01
 */

public class Matrix {
  double[][] val; // The local variable storing matrix entries
  
  /**
   * Constructor: Create a matrix object with max(1,m) rows and max(1,n) columns.
   *
   * @param m the number of rows
   * @param n the number of columns
   **/
  public Matrix(int m, int n) {
    
	if((m < 1) || (n < 1)) {
      	val = new double[1][1];
	} else {
      val = new double[m][n];
    }
  }
  
  /**
   * Check if the values of 2 matrices are equal
   *
   * @param B any matrix
   * @return a boolean, true if no differences were found
   **/
  public boolean equals(Matrix B) {
    if((rows() != B.rows()) || (cols() != B.cols())) {
      return false;
    }
    else {
      for(int i = 1; i <= rows(); i++) {
        for(int j = 1; j <= cols(); j++) {
          if((get(i,j)-B.get(i,j))>1e-10) {
            return false;
          }
        }
      }
    }
    return true;
  }
  
  /**
   * Create a string of the matrix's content
   *
   * @return a string
   **/
  public String toString() {
    String str = "";
    
	for(int i = 1; i <= rows(); i++) {
      str = str+"[";
      for(int j = 1; j <= cols(); j++) {
        if(j>1) {
          str = str+" ";
        }
        str = str+get(i, j);
      }
      str = str+"]";
      if(i != rows()) {
        str = str+"\n";
      }
	}

    return str;
  }
  
  /**
   * Create an identity matrix object with n rows and n columns
   *
   * @param n the number of rows and columns
   * @return a new matrix of size n x n, whos diagonal contains 1 and off-diagonal 0
   * @throws IllegalArgumentException if n is smaller than 1
   **/
  public static Matrix identity(int n) {
    if(n<1) {
      throw new IllegalArgumentException("Size of matrix must be larger than 0");
    }

    Matrix I = new Matrix(n,n);

	for(int i = 1; i <= n; i++) {
      for(int j = 1; j <= n; j++) {
		if(i==j) {
          I.set(i, j, 1);
        } else {
          I.set(i, j, 0);
        }
      }
	}

    return I;
  }
  
  /**
   * Create a constant matrix object with n rows and m columns containing values v
   *
   * @param m the number of rows
   * @param n the number of columns
   * @param v any value
   * @return a new matrix of size n x m, whos elemenst are v
   * @throws IllegalArgumentException if n is smaller than 1
   **/
  public static Matrix constant(int m, int n, double v) {
    if((n<1) || (m<1)) {
      throw new IllegalArgumentException("Number of rows and/or columns must be larger than 0");
    }

    Matrix C = new Matrix(m,n);

	for(int i = 1; i <= m; i++) {
      for(int j = 1; j <= n; j++) {
        C.set(i, j, v);
      }
	}

    return C;
  }
  
  /**
   * Return the number of rows in the matrix.
   *
   * @return the number of rows
   **/
  public int rows() {
	return val.length;
  }
  
  /**
   * Return the number of columns in the matrix.
   *
   * @return the number of columns
   **/
  public int cols() {
	return val[0].length;
  }

  /**
   * Set value at row m and column n to be v.
   *
   * @param m the row number
   * @param n the column number
   * @param v the value
   * @throws IndexOutOfBoundsException if  {@literal (m < 1) || (m > rows()) || (n < 1) || (n > cols())}
   **/
  public void set(int m, int n, double v) {
	if((m < 1) || (m > rows()) || (n < 1) || (n > cols())) {
      throw new IndexOutOfBoundsException("Cannot set a value outside the index domain");
	}

	val[m-1][n-1] = v;
  }
  
  /**
   * Change all values to be v.
   *
   * @param v any value
   **/
  public void set(double v) {
	for(int i = 1; i <= rows(); i++) {
      for(int j = 1; j <= cols(); j++) {
		set(i, j, v);
      }
	}
  }
  
  /**
   * Return the matrix value at row m and column n.
   *
   * @param m the row number
   * @param n the column number
   * @return the value at (m,n)
   * @throws IndexOutOfBoundsException if {@literal (m < 1) || (m > rows()) || (n < 1) || (n > cols())}
   **/
  public double get(int m, int n) {
	if((m < 1) || (m > rows()) || (n < 1) || (n > cols())) {
      throw new IndexOutOfBoundsException("Cannot access a value outside the index domain");
	}

	return val[m-1][n-1];
  }

  /**
   * Create a new matrix, which is a copy of this, but where row and column values have been interchanged.
   *
   * @return a new matrix
   **/
  public Matrix transpose() {
    
	Matrix M = new Matrix(cols(), rows());
    
	for(int i = 1; i <= cols(); i++) {
	    for(int j = 1; j <= rows(); j++) {
		M.set(i, j, get(j, i));
	    }
	}
    
	return M;
  }
    
  /**
   * Pretty-print the matrix to stdout.
   **/
  public void println() {
    System.out.println(toString());
  }

  /**
   * Create a new matrix, which is the element-wise addition of this with another matrix B.  The two matrices must be of the same size.
   *
   * @param B a matrix
   * @return a new matrix
   * @throws IllegalArgumentException if number of rows and columns differs in this and B
   **/
  public Matrix add(Matrix B) {
	if((rows()!=B.rows()) || (cols()!=B.cols())) {
      throw new IllegalArgumentException("Number of rows and columns differ");
	}
    
	Matrix M = new Matrix(rows(), cols());
	double v;
    
	for(int i = 1; i <= rows(); i++) {
      for(int j = 1; j <= cols(); j++) {
		v = get(i, j);
		M.set(i, j, v+B.get(i, j));
      }
	}
    
	return M;
  }    
  
  /**
   * Create a new matrix which is the element-wise addition of this with v.
   *
   * @param v any value
   * @return a new matrix of same size as this
   **/
  public Matrix add(double v) {
	Matrix M = new Matrix(rows(), cols());
    
	for(int i = 1; i <= rows(); i++) {
      for(int j = 1; j <= cols(); j++) {
		M.set(i, j, v+get(i, j));
      }
	}
    
	return M;
  }    
  
  /**
   * Create a new matrix which is the element-wise multiplication of this with v.
   *
   * @param v any value
   * @return a new matrix same size as this
   **/
  public Matrix mul(double v) {
	Matrix M = new Matrix(rows(), cols());
    
	for(int i = 1; i <= rows(); i++) {
      for(int j = 1; j <= cols(); j++) {
		M.set(i, j, v*get(i, j));
      }
	}
    
	return M;
  }    
  
  /**
   * Create a new matrix which is the concatenation of this with B, i.e. [this | B].
   *
   * @param B a matrix
   * @return a new matrix whos size is rows() x (cols() + B.cols())
   * @throws IllegalArgumentException if this and B have different number of rows
   **/
  public Matrix concatenateRows(Matrix B) {
	if(rows() != B.rows()) {
      throw new IllegalArgumentException("The number of rows must be identical");
	}
    
    Matrix M = new Matrix(rows(), cols()+B.cols());
    
	for(int i = 1; i <= M.rows(); i++) {
      for(int j = 1; j <= cols(); j++) {
		M.set(i, j, get(i, j));
      }
      for(int j = 1; j <= B.cols(); j++) {
		M.set(i, j+cols(), B.get(i, j));
      }
	}
    
	return M;
  }
  
  /**
   * Create a new matrix whos elements are copied from this matrix from rows from_row .. to_row and columns from_col .. to_col.
   *
   * @param from_row a valid row index
   * @param from_col a valid column index
   * @param to_row a valid row index {@literal >} from_row
   * @param to_col a valid column index {@literal >} from_col
   * @return a new matrix of size (to_row - from_row + 1) x (to_col - from_col + 1)
   * @throws IndexOutOfBoundsException if any of the arguments are outside the matrix's valid index domain
   **/
  public Matrix subMatrix(int from_row, int from_col, int to_row, int to_col) {
	if((from_row < 1) || (from_row > rows()) || (from_col < 1) || (from_col > cols()) || (to_row < from_row) || (to_row > rows()) || (to_col < from_col) || (to_col>cols())) {
      throw new IndexOutOfBoundsException("Cannot access values outside the index domain");
	}
    
	int ROWS = to_row - from_row + 1;
	int COLS = to_col - from_col + 1;
	Matrix M = new Matrix(ROWS, COLS);
    
	for(int i=1; i<=ROWS; i++) {
      for(int j=1;j<=COLS;j++) {
		M.set(i, j, get(i+from_row-1, j+from_col-1));
      }
    }
	return M;
  }
  
  /**
   * Create a new matrix, where rows a and b are interchanged
   *
   * @param a a valid row index
   * @param b a valid column index
   * @return a new matrix of same size as this
   * @throws IndexOutOfBoundsException if a or b are outside the valid index domain of this matrix
   **/
  public Matrix swapRows(int a, int b) {
	if((a < 1) || (a>rows()) || (b<1) || (b>rows())) {
      throw new IndexOutOfBoundsException("Cannot access values outside the index domain");
	}
    
	Matrix M = add(0);
    
	if(a!=b) {
      for(int i = 1; i <= rows(); i++) {
		if(i == a) {
          for(int j = 1; j <= cols(); j++) {
			M.set(b, j, get(i, j));
          }
		} else if(i==b) {
          for(int j = 1; j <= cols(); j++) {
			M.set(a, j, get(i, j));
          }
		}
		else {
          for(int j = 1; j <= cols(); j++) {
			M.set(i, j, get(i, j));
          }
		}
      }
	}
    
	return M;
  }
  
  /**
   * Create a new matrix, where row a has been replaced by the sum of row b plus v times row c.
   *
   * @param a a valid row index
   * @param b a valid row index
   * @param c a valid row index
   * @param v any number
   * @return a new matrix
   * @throws IndexOutOfBoundsException if any of a, b, or c are outside this matrix's valid row index domain
   **/
  public Matrix addMulRows(int a, int b, int c, double v) {
	if((a < 1) || (a>rows()) || (b<1) || (b>rows()) || (c<1) || (c>rows())) {
      throw new IndexOutOfBoundsException("Cannot access values outside the index domain");
	} 
    
	Matrix M = add(0);
    
	for(int i = 1; i <= rows(); i++) {
      for(int j = 1; j <= cols(); j++) {
		if(i == a) {
          M.set(a, j, get(b, j)+v*get(c, j));
		} else {
          M.set(i, j, get(i, j));
		}
      }
	}
    
	return M;
  }
  
  /**
   * Create a new matrix as a copy of this excluding the i'th column.
   *
   * @param a valid column index
   * @return a new matrix whos size is rows() x (cols() - 1)
   * @throws IndexOutOfBoundsException if a is outside this matrix's column index domain
   **/
  public Matrix deleteCol(int a) {
	if((a < 1) || (a>cols())) {
      throw new IndexOutOfBoundsException("Cannot delete a column outside the index domain");
	}
    
	Matrix M;
    
	if(a == 1) {
      M = subMatrix(1, 2, rows(), cols());
	} else if(a == cols()) {
      M = subMatrix(1, 1, rows(), cols()-1);
	} else {
      M = subMatrix(1, 1, rows(), a-1);
      M = M.concatenateRows(subMatrix(1, a+1, rows(), cols()));
	}
    
	return M;
  }
  
  /**
   * Create a new matrix as a copy of this where column a is replaced with B.
   *
   * @param a a valid column index
   * @param B a matrix whos size is rows() x 1
   * @return a new matrix
   * @throws IndexOutOfBoundsException if a is outside this matrix's valid column index domain
   * @throws IllegalArgumentException if b does is not of size rows() x 1
   **/
  public Matrix replaceCol(int a, Matrix B) {
	if((a < 1) || (a>cols())) {
      throw new IndexOutOfBoundsException("Cannot replace a column outside the index domain");
	}
	if((B.rows() != rows()) || (B.cols() != 1)) {
      throw new IllegalArgumentException("Must be a matrix of size rows() x 1");
	}
    
	Matrix M = add(0);
    
	for(int i=1; i <= rows(); i++) {
      M.set(i,a,B.get(i,1));
	}
	return M;
  }
  

}
