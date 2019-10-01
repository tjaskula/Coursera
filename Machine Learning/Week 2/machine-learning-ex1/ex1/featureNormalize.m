function [x_norm, mu, sigma] = featureNormalize(x)
%FEATURENORMALIZE Normalizes the features in X 
%   FEATURENORMALIZE(X) returns a normalized version of X where
%   the mean value of each feature is 0 and the standard deviation
%   is 1. This is often a good preprocessing step to do when
%   working with learning algorithms.

% You need to set these values correctly
x_norm=x;

%x_norm(:,2) = (x(:,2)-mu(:,2))/sigma(:,2);
%x_norm(:,1) = (x(:,1)-mu(:,1))/sigma(:,1);
% ====================== YOUR CODE HERE ======================
% Instructions: First, for each feature dimension, compute the mean
%               of the feature and subtract it from the dataset,
%               storing the mean value in mu. Next, compute the 
%               standard deviation of each feature and divide
%               each feature by it's standard deviation, storing
%               the standard deviation in sigma. 
%
%               Note that X is a matrix where each column is a 
%               feature and each row is an example. You need 
%               to perform the normalization separately for 
%               each feature. 
%
% Hint: You might find the 'mean' and 'std' functions useful.
%       


mu = mean(x);
sigma = std(x);
x_norm =(x.- mean(x,1))./std(x,0,1);






% ============================================================

end
