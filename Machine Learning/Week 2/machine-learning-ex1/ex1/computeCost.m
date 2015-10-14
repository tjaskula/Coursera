function J = computeCost(X, y, theta)
%COMPUTECOST Compute cost for linear regression
%   J = COMPUTECOST(X, y, theta) computes the cost of using theta as the
%   parameter for linear regression to fit the data points in X and y

% Initialize some useful values
m = length(y); % number of training examples

% You need to return the following variables correctly 
J = 0;

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the cost of a particular choice of theta
%               You should set J to the cost.

% The result is a vector (h). The vector must be the second term 
% of the multiplication. The number of rows of the vector must equal 
% the number of columns of the matrix. That's why X is first and theta second
h = X * theta; % hypothesis function (Theta' * x = theta_0 + theta_1 * x_1)
squaredErrors = (h - y) .^ 2;
J = (1 / (2 * m)) * sum(squaredErrors);

% =========================================================================

end